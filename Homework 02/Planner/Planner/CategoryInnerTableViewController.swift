//
//  CategoryInnerTableViewController.swift
//  Planner
//
//  Created by Bennie Chen on 6/17/21.
//

/*
 Name: Bennie Chen
 ID: 112737201
 CSE 390: Mobile App Development
 Homework 2
 */

import UIKit
import CoreData

class AssignmentInnerViewCell: UITableViewCell{
    @IBOutlet weak var assignmentView: UILabel!
    @IBOutlet weak var categoryView: UILabel!
    @IBOutlet weak var dateView: UILabel!
}

class CategoryInnerTableViewController: UITableViewController {
    
    var category: String = ""
    var tasks: [NSManagedObject] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title? = category
        self.navigationItem.rightBarButtonItem = self.editButtonItem
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
        let request = NSFetchRequest<NSManagedObject>(entityName: "Task")
        let sortDescriptor = NSSortDescriptor(key: "date", ascending: true)
        let sortDescriptorArray = [sortDescriptor]
        request.sortDescriptors = sortDescriptorArray
        let predicate = NSPredicate(format: "category = %@", category)
        request.predicate = predicate
        do{
            tasks = try context.fetch(request)
        } catch let error as NSError {
            print("Could not fetch \(error)")
        }
    }
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return tasks.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "SpecificCategorryCell", for: indexPath) as! AssignmentInnerViewCell

        // Configure the cell...
        let event = tasks[indexPath.row] as! Task
        cell.assignmentView?.text = event.assignment
        cell.categoryView?.text = event.category
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        formatter.setLocalizedDateFormatFromTemplate("MM/dd")
        cell.dateView?.text = formatter.string(from: (event.date)!)
        let delta = (event.date)!.timeIntervalSinceReferenceDate - Date().timeIntervalSinceReferenceDate
        if delta < (3600 * 24)  && delta > 0 {
            cell.assignmentView?.textColor = UIColor.red
            cell.dateView?.textColor = UIColor.red
        }
        else if delta >= (3600 * 24) && delta <= (3600 * 24 * 2){
            cell.assignmentView?.textColor = UIColor.blue
            cell.dateView?.textColor = UIColor.blue
        }
        else if delta < 0 {
            cell.assignmentView?.textColor = UIColor.lightGray
            cell.dateView?.textColor = UIColor.lightGray
        }
        else {
            cell.assignmentView?.textColor = UIColor.black
            cell.dateView?.textColor = UIColor.black
        }
        return cell
    }
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let task = tasks[indexPath.row] as? Task
            let context = appDelegate.persistentContainer.viewContext
            context.delete(task!)
            do {
                try context.save()
            } catch {
                fatalError("Error Saving Context \(error)")
            }
            loadDataFromDatabase()
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }


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
        if segue.identifier ==  "EditInnerTask" {
            let taskController = segue.destination as? AssignmentsViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            let selectedTask = tasks[selectedRow!] as? Task
            taskController?.currentEvent = selectedTask
        }
    }
    

}
