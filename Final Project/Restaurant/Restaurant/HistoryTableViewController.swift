//
//  HistoryTableViewController.swift
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
 This class is the definition of a History View Cell that is in the History View
 */
class HistoryCellView: UITableViewCell {
    @IBOutlet weak var nameView: UILabel!
    @IBOutlet weak var countView: UILabel!
    @IBOutlet weak var priceView: UILabel!
    @IBOutlet weak var dateView: UILabel!
    
}

class HistoryTableViewController: UITableViewController {
    
    var historyData: [Table] = []
    let ref = Database.database().reference()
    let settings = UserDefaults.standard
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        loadFromDatabase()
        tableView.reloadData()
    }
    
    /**
     This function loads the data from the database
     */
    func loadFromDatabase() {
        historyData.removeAll()
        
        let query = "customerHistory/"

        ref.child(query).observeSingleEvent(of: .value) { [self] (snapshot) in
            for child in snapshot.children{
                let snap = child as! DataSnapshot
                let value = snap.value as? NSDictionary
                let name = value?["name"] as? String ?? ""
                let count = value?["count"] as? Int ?? 0
                let phone = value?["phone"] as? String ?? ""
                let time = value?["time"] as? Double ?? 0
                let price = value?["price"] as? Double ?? 0.0
                let order = value?["order"] as? [Int] ?? []
                historyData.append(Table(tableNumber: snap.key, name: name, count: count, phone: phone, time: time, price: price, order: order))
                sortArray()
            }
        }
        print("here")
    }
    /**
     This fucntion sorts the array based on the settings
     */
    func sortArray(){
        let sortType = settings.string(forKey: Constants.kSortField)
        let sortDirection = settings.bool(forKey: Constants.kSortDirectionAscending)
        //print("Sort Field \(sortType ?? "Date")   Sort Direction: \(sortDirection)")
        if(sortType == "Date" && sortDirection == true) {
            historyData.sort(by: {$0.time > $1.time})
        }
        else if(sortType == "Date" && sortDirection == false) {
            historyData.sort(by: {$0.time < $1.time})
        }
        else if(sortType == "Name" && sortDirection == true) {
            historyData.sort(by: {$0.name > $1.name})
        }
        else if(sortType == "Name" && sortDirection == false) {
            historyData.sort(by: {$0.name < $1.name})
        }
        else if(sortType == "Number of People" && sortDirection == true) {
            historyData.sort(by: {$0.count > $1.count})
        }
        else if(sortType == "Number of People" && sortDirection == false) {
            historyData.sort(by: {$0.count < $1.count})
        }
        else if(sortType == "Price" && sortDirection == true) {
            historyData.sort(by: {$0.price > $1.price})
        }
        else if(sortType == "Price" && sortDirection == false) {
            historyData.sort(by: {$0.price < $1.price})
        }
        tableView.reloadData()
    }

    // MARK: - Table view data source

    /**
     This function returns the number of sections in the table
     - returns: int value with the number of sections in the table
     */
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    /**
     This function returns the number of counts in the table
     - returns: int representing the number of rows in the history data view
     */
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return historyData.count
    }

    /**
     This function custimizes the cell
     - returns: UITableCellView object that represents the custimezed cell
     */
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "HistoryCell", for: indexPath) as! HistoryCellView

        // Configure the cell...
        let hisOrder = historyData[indexPath.row]
        cell.nameView?.text = hisOrder.name
        cell.countView?.text = "Group of \(hisOrder.count)"
        cell.priceView?.text = String(format: "$%.2f", hisOrder.price)
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        formatter.setLocalizedDateFormatFromTemplate("MM/dd")
        cell.dateView?.text = formatter.string(from: NSDate(timeIntervalSince1970: hisOrder.time) as Date)
        return cell
    }

    
    /**
     This method overrdes the deleting of  entries
     */
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            let entry = historyData[indexPath.row]
            let query = "customerHistory/\(entry.tableNumber)"
            ref.child(query).removeValue()
            var numOrders = settings.integer(forKey: Constants.knumOrders)
            numOrders -= 1
            settings.setValue(numOrders, forKey: Constants.knumOrders)
            var moneyTotal = settings.double(forKey: Constants.koverallTotal)
            moneyTotal -= Double(entry.price)
            settings.setValue(moneyTotal, forKey: Constants.koverallTotal)
            loadFromDatabase()
            tableView.reloadData()
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }

    
    // MARK: - Navigation

    
    /**
     This method prepares fornavigation and fnds the segue
     */
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "historyTablePath" {
            let entryController = segue.destination as? HistoryOrderViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            entryController?.newOrder = historyData[selectedRow!]
        }
    }
    

}
