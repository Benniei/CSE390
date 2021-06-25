//
//  ReceiptTableViewController.swift
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
 This class is the definition of a Reciept View Cell which resides in the Reciept View
 */
class ReceiptCellView: UITableViewCell{
    @IBOutlet weak var tableView: UILabel!
    @IBOutlet weak var countView: UILabel!
    @IBOutlet weak var priceView: UILabel!
}

class ReceiptTableViewController: UITableViewController {
    
    let tables = ["table1", "table2", "table3", "table4", "table5", "table6"]
    var tableData: [Table] = []
    let ref = Database.database().reference()
    let settings = UserDefaults.standard
    
    /**
     This function is called when the view was loaded
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        self.navigationItem.leftBarButtonItem = self.editButtonItem
    }
    /**
     This function is called when the view appears
     */
    override func viewDidAppear(_ animated: Bool) {
        loadFromDatabase()
        tableView.reloadData()
    }
    
    /**
     This function is called to load the data from firrebase database
     */
    func loadFromDatabase() {
        tableData.removeAll()
        for table in tables{
            let query = "currentTable/\(table)"
            
            ref.child(query).observeSingleEvent(of: .value) { [self] (snapshot) in
                let value = snapshot.value as? NSDictionary
                if !snapshot.exists() {return}
                
                if let isUse = value?["inUse"] as? Int {
                    if isUse == 0 {
                        return
                    }
                }
                let name = value?["name"] as? String ?? ""
                let count = value?["count"] as? Int ?? 0
                let phone = value?["phone"] as? String ?? ""
                let time = value?["time"] as? Double ?? 0
                let price = value?["price"] as? Double ?? 0.0
                let order = value?["order"] as? String ?? "0000000"
                var intArray: [Int] = []
                for chr in order {
                    intArray.append(Int(String(chr)) ?? 0)
                }
                tableData.append(Table(tableNumber: table, name: name, count: count, phone: phone, time: time, price: price, order: intArray))
                tableView.reloadData()
            }
            
        }
        
        
    }

    // MARK: - Table view data source

    /**
     This method returns the number of sections
     - returns: int representing the numbert of sections
     */
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    /**
     This method returns the number of rows
     - returns: int representing the number of rows
     */
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return tableData.count
    }

    /**
     This method  customizes the  cell view of the table
     - returns: UITableViewCell object rereseneting the cell
     */
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TableCell", for: indexPath) as! ReceiptCellView

        // Configure the cell...
        let table = tableData[indexPath.row]
        cell.tableView?.text = "Table \(Array(table.tableNumber)[5])"
        cell.countView?.text = "Group of \(table.count)"
        cell.priceView?.text = String(format: "$%.2f", table.price)
        return cell
    }
    
    /**
     This function overrides the  delete function
     */
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            let table = tableData[indexPath.row]
            let query = "currentTable/\(table.tableNumber)"
            let queryIn = "customerHistory/"
            ref.child(queryIn).childByAutoId().setValue(["name":table.name, "count":table.count, "order":table.order, "phone":table.phone, "price": table.price, "time": table.time])
            ref.child(query + "/inUse").setValue(0)
            ref.child(query + "/name").setValue("")
            ref.child(query + "/count").setValue(0)
            ref.child(query + "/phone").setValue("")
            ref.child(query + "/time").setValue(0)
            ref.child(query + "/order").setValue("0000000")
            ref.child(query + "/price").setValue(0)
            
            
            var numOrders = settings.integer(forKey: Constants.knumOrders)
            numOrders += 1
            settings.setValue(numOrders, forKey: Constants.knumOrders)
            loadFromDatabase()
            tableView.reloadData()
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    

    
    // MARK: - Navigation

    /**
     This function prepares for segue and passes the table to thre Order View
     */
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "tableToView" {
            let entryController = segue.destination as? OrderViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            entryController?.entryQuery = tables[selectedRow!]
        }
    }


}
