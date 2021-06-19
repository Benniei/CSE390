//
//  CategoryTableViewController.swift
//  Planner
//
//  Created by Bennie Chen on 6/17/21.
//

import UIKit
import CoreData

struct simpleCategory {
    var catName: String
    var count: Int
}
class CategoryCell: UITableViewCell{
    @IBOutlet weak var categoryView: UILabel!
    @IBOutlet weak var countView: UILabel!
}

class CategoryTableViewController: UITableViewController {
    
    var onlyCategory: [String] = []
    var categories: [simpleCategory] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        loadDataFromDatabase()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        loadDataFromDatabase()
        tableView.reloadData()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func loadDataFromDatabase() {
        let context = appDelegate.persistentContainer.viewContext
        //create requrest for entity
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Task")
        request.resultType = .dictionaryResultType
        request.returnsDistinctResults = true
        request.propertiesToFetch = ["category"]
        if let res = try? context.fetch(request) as? [[String: String]] {
            // print("res: \(res)")
            // 8. Extract the distinct values
            onlyCategory = res.compactMap { $0["category"] }
        }
        categories.removeAll()
        for cat in onlyCategory{
            categories.append(simpleCategory(catName: cat, count: 0))
        }
        var tasks: [NSManagedObject] = []
        let newRequest = NSFetchRequest<NSManagedObject>(entityName: "Task")
        do{
            tasks = try context.fetch(newRequest)
        } catch let error as NSError {
            print("Could not fetch \(error)")
        }
        for i in 0...tasks.count - 1{
            let task = tasks[i] as! Task
            for j in 0...categories.count - 1{
                if categories[j].catName == task.category{
                    categories[j].count = categories[j].count + 1
                }
            }
        }
        while true {
            var number = -1
            for i in 0...categories.count - 1{
                if categories[i].count == 0 {
                    number = i
                }
            }
            if number == -1 {
                break
            }
            else {
                categories.remove(at: number)
            }
        }
    }
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return categories.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "CategoryCell", for: indexPath) as! CategoryCell

        // Configure the cell...
        let cat = categories[indexPath.row]
        cell.categoryView.text = cat.catName
        cell.countView.text = String(cat.count)
        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier ==  "displayCategory" {
            let taskController = segue.destination as? CategoryInnerTableViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            let selectedTask = onlyCategory[selectedRow!]
            taskController?.category = selectedTask
        }
    }
    

}
