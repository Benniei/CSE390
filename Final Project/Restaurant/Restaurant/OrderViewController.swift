//
//  OrderViewController.swift
//  Restaurant
//
//  Created by Bennie Chen on 6/22/21.
//

/*
 * Bennie Chen
 * ID: 112737201
 * CSE 390: MObile App Development
 * Final Project
 * Icon links
 *      https://thenounproject.com/term/minimal/
 *      https://www.surrenderat20.net/2019/07/731-pbe-update-new-summoner-icons-tft.html
 */

import UIKit
import FirebaseDatabase

/**
 This class is the definition of a custom cell which resides in the OrderView
 */
class MenuOrdeCellView: UITableViewCell {
    @IBOutlet weak var foodView: UILabel!
    @IBOutlet weak var priceView: UILabel!
    @IBOutlet weak var amountView: UILabel!
    @IBOutlet weak var addButton: UIButton!
    @IBOutlet weak var minusButton: UIButton!
    
    var foodPosition: Int?
    var foodObject: Food?
    var totalView: UILabel!
    let settings = UserDefaults.standard
    
    /**
     This method adds to the number of order that is being displayed
     */
    @IBAction func addAmount(_ sender: Any) {
        var amount = Int(amountView.text!)
        amount = amount! + 1
        foodObject!.foodAmount += 1
        var currentTotal = Double(totalView.text!)
        currentTotal! += foodObject!.foodPrice
        if amount == 10 {
            amount = 9
            foodObject!.foodAmount -= 1
            currentTotal! -= foodObject!.foodPrice
        }

        totalView.text = String(format: "%.2f", currentTotal!)
        amountView.text = String(format:"%d", amount!)
    }
    
    /**
     This function subtracts the number of orders taht is being displayed
     */
    @IBAction func subAmount(_ sender: Any) {
        var amount = Int(amountView.text!)
        amount = amount! - 1
        foodObject!.foodAmount -= 1
        var currentTotal = Double(totalView.text!)
        currentTotal! -= foodObject!.foodPrice
        if amount == -1 {
            amount = 0
            foodObject!.foodAmount += 1
            currentTotal! += foodObject!.foodPrice
        }
        totalView.text = String(format: "%.2f", currentTotal!)
        amountView.text = String(format:"%d", amount!)
    }
    
}

class OrderViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, UITextFieldDelegate {
    
    
    @IBOutlet weak var editSwitch: UISegmentedControl!
    @IBOutlet weak var nameEntry: UITextField!
    @IBOutlet weak var countEntry: UITextField!
    @IBOutlet weak var phoneEntry: UITextField!
    @IBOutlet weak var timeEntry: UIDatePicker!
    @IBOutlet weak var totalView: UILabel!
    @IBOutlet weak var table: UITableView!
    
    var menu: [Food] = []
    
    var entryQuery: String?
    let ref = Database.database().reference()
    let settings = UserDefaults.standard
    var userTotal: Double?
    
    /**
     This Function loads the view for Order View Controller ands sets the views
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        self.navigationItem.title? = "Table \(Array(entryQuery ?? "")[5])"
        self.table.delegate = self
        self.table.dataSource = self
        table.allowsSelection = false
        navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: .save, target: self, action: #selector(self.saveEntry))
        loadFromDatabase()
        // Do any additional setup after loading the view.
        userTotal = Double(totalView.text!)
        self.changedEditMode(self)
    }
    
    /**
     This function loads the necessary fields from the database
     */
    func loadFromDatabase(){
        let query = "currentTable/\(entryQuery ?? "")"
        ref.child(query).observeSingleEvent(of: .value) { (snapshot) in
            let value = snapshot.value as? NSDictionary
            if !snapshot.exists() {return}
            
            if let isUse = value?["isUse"] as? Int {
                if isUse == 0 {
                    self.addEmptyMenu()
                    return
                }
            }
            
            if let name = value?["name"] as? String {
                self.nameEntry.text = name
            }
            if let count = value?["count"] as? Int {
                self.countEntry.text = String(count)
            }
            if let phone = value?["phone"] as? String {
                self.phoneEntry.text = phone
            }
            if let time = value?["time"] as? Double {
                if(time != 0){
                    let date = NSDate(timeIntervalSince1970: time)
                    self.timeEntry.date = date as Date
                }
            }
            if let price = value?["price"] as? Double {
                self.totalView.text = String(format: "%.2f", price)
            }
            if let order = value?["order"] as? String {
                var intArray: [Int] = []
                for chr in order {
                    intArray.append(Int(String(chr)) ?? 0)
                }
                self.addEmptyMenu()
                for i in 0 ... self.menu.count - 1 {
                    self.menu[i].foodAmount = intArray[i]
                }
            }
            self.table.reloadData()
        }
    }
    
    /**
     This function adds all the menu items for a new customer
     */
    func addEmptyMenu(){
        menu.append(Food(foodName: "Curry Chicken", foodAmount: 0, foodPrice: 13.00))
        menu.append(Food(foodName: "Salmon Teriyaki", foodAmount: 0, foodPrice: 15.00))
        menu.append(Food(foodName: "Rice", foodAmount: 0, foodPrice: 1.50))
        menu.append(Food(foodName: "Mille Crepe Cake", foodAmount: 0, foodPrice: 9.00))
        menu.append(Food(foodName: "Soufflé Pankcake", foodAmount: 0, foodPrice: 12.00))
        menu.append(Food(foodName: "Dalgona Coffee", foodAmount: 0, foodPrice: 4.25))
        menu.append(Food(foodName: "Water", foodAmount: 0, foodPrice: 1.00))
    }
    
    /**
     Saves the entry into the database
     */
    @objc func saveEntry(){
        let current = Double(totalView.text!)
        let difference = current! - userTotal!
        userTotal = current
        var overallTotal = settings.double(forKey: Constants.koverallTotal)
        overallTotal += difference
        settings.setValue(overallTotal, forKey: Constants.koverallTotal)
        let query = "currentTable/\(entryQuery ?? "")"
        ref.child(query + "/inUse").setValue(1)
        ref.child(query + "/name").setValue(nameEntry.text?.trimmingCharacters(in: .whitespaces))
        ref.child(query + "/count").setValue(Int(countEntry.text!))
        ref.child(query + "/phone").setValue(phoneEntry.text?.trimmingCharacters(in: .whitespaces))
        ref.child(query + "/time").setValue(timeEntry.date.timeIntervalSince1970)
        var list: [Int] = []
        for entry in menu {
            list.append(entry.foodAmount)
        }
        let stringVersion = list.map {String($0)}
        let stringOrder = stringVersion.joined(separator: "")
        ref.child(query + "/order").setValue(stringOrder)
        ref.child(query + "/price").setValue(Double(totalView.text!))
        editSwitch.selectedSegmentIndex = 0
        changedEditMode(self)
    }
    
    /**
     Changes the edit mode of th view and allows for the information fields to be edited
     */
    @IBAction func changedEditMode(_ sender: Any) {
        let textFields: [UITextField] =  [nameEntry, countEntry, phoneEntry]
        if editSwitch.selectedSegmentIndex == 0 {
            for textField in textFields{
                textField.isEnabled = false
                textField.borderStyle = UITextField.BorderStyle.none
            }
            timeEntry.isEnabled = false
        }
        else if editSwitch.selectedSegmentIndex == 1 {
            for textField in textFields {
                textField.isEnabled = true
                textField.borderStyle = UITextField.BorderStyle.roundedRect
            }
            timeEntry.isEnabled = true
            // Creates the Save button on the right half of the navigation bar
        }
        table.reloadData()
    }
    
    // MARK: - Table view data source
    
    /**
     This function returns the number of cells in the table
     - returns: number of cells
     */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return menu.count // your number of cells here
    }
    /**
     This function customizes the cells in the table
     - returns: cell that was customized
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "ReceiptCell", for: indexPath) as! MenuOrdeCellView
        
        let item = menu[indexPath.row]
        cell.foodView?.text = item.foodName
        cell.priceView?.text = String(format: "%.2f", item.foodPrice)
        cell.amountView?.text = String(format: "%d", item.foodAmount)
        cell.foodPosition = indexPath.row
        cell.totalView = totalView
        cell.foodObject = item
        if timeEntry.isEnabled == false {
            cell.minusButton.isEnabled = false
            cell.addButton.isEnabled = false
        }
        else {
            cell.minusButton.isEnabled = true
            cell.addButton.isEnabled = true
        }
        return cell
    }

}
