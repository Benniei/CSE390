//
//  HistoryOrderViewController.swift
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
 This class is the definition of History Order View cell which resides in the Order HIstor View
 */
class HistoryOrderViewCell: UITableViewCell{
    @IBOutlet weak var foodView: UILabel!
    @IBOutlet weak var priceView: UILabel!
    @IBOutlet weak var amountView: UILabel!
}

class HistoryOrderViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    @IBOutlet weak var nameView: UILabel!
    @IBOutlet weak var phoneView: UILabel!
    @IBOutlet weak var countView: UILabel!
    @IBOutlet weak var timeView: UIDatePicker!
    @IBOutlet weak var totalaView: UILabel!
    @IBOutlet weak var table: UITableView!
    
    var newOrder: Table?
    var order: [Food] = []
    var menu: [Food] = []
    
    /**
     This function is called when the view is loaded and loads up all the information on the page
     */
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        self.nameView.text = newOrder?.name
        self.phoneView.text = newOrder?.phone
        let count = newOrder?.count
        self.countView.text = String(count!)
        let time = (newOrder?.time)!
        let date = NSDate(timeIntervalSince1970: time)
        self.timeView.date = date as Date
        let price = newOrder?.price
        self.totalaView.text = String(format: "%.2f", price!)
        addEmptyMenu()
        let foodOrder = (newOrder?.order)!
        for i in 0...menu.count - 1 {
            if foodOrder[i] > 0 {
                order.append(Food(foodName: menu[i].foodName, foodAmount: foodOrder[i], foodPrice: menu[i].foodPrice))
            }
        }
        self.table.delegate = self
        self.table.dataSource = self
        table.allowsSelection = false
    }
    
    /**
     This method fills in the meu array with the menu
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
    
    
    // MARK: - Table view data source
    
    /**
     This function returns the number of rows there will be in the fnction
     */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return order.count // your number of cells here
    }
    
    /**
     This function custimizes the cell used to display the food
     */
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "HistoryItemCell", for: indexPath) as! HistoryOrderViewCell
        
        let item = order[indexPath.row]
        cell.foodView?.text = item.foodName
        cell.priceView?.text = String(format: "%.2f", item.foodPrice)
        cell.amountView?.text = String(format: "%d", item.foodAmount)
        return cell
    }

}
