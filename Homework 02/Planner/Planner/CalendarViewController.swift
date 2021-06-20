//
//  CalendarViewController.swift
//  Planner
//
//  Created by Bennie Chen on 6/19/21.
//

import UIKit
import FSCalendar
import CoreData

class AssignmentCalendarCell: UITableViewCell {
    
    @IBOutlet weak var assignmentView: UILabel!
    @IBOutlet weak var categoryView: UILabel!
    @IBOutlet weak var dateView: UILabel!
}

class CalendarViewController: UIViewController, FSCalendarDelegate, UITableViewDataSource, UITableViewDelegate {
    
    var tasks: [NSManagedObject] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    var dateCurrent: Date?
    
    @IBOutlet weak var calendar: FSCalendar!
    @IBOutlet weak var tabel: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        calendar.delegate = self
        self.tabel.delegate = self
        self.tabel.dataSource = self
        self.navigationItem.leftBarButtonItem = self.editButtonItem
        let date = Date()
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from:date)
        let minute = calendar.component(.minute, from:date)
        loadDataFromDatabase(date: date.addingTimeInterval(-(Double((hour*3600) + (minute*60)))))
    }
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        dateCurrent = date
        loadDataFromDatabase(date: date)
        tabel.reloadData()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        tabel.reloadData()
    }
    
    func loadDataFromDatabase(date: Date){
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSManagedObject>(entityName: "Task")
        let startDate = date.timeIntervalSinceReferenceDate
        let endDate = startDate + (3600 * 24)
        do{
            tasks = try context.fetch(request)
        } catch let error as NSError {
            print("Could not fetch \(error)")
        }
        while true {
            var number = -1
            for i in 0...tasks.count - 1{
                let task = tasks[i] as! Task
                let time = (task.date)!.timeIntervalSinceReferenceDate
                if !(time >= startDate && time < endDate) {
                    number = i
                    break
                }
            }
            if number == -1 {
                break
            }
            else {
                tasks.remove(at: number)
                if tasks.count == 0 {
                    break
                }
            }
        }
    }
    
    //Table setup
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tasks.count // your number of cells here
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "AssignmenCalendarCell", for: indexPath) as! AssignmentCalendarCell

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
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let task = tasks[indexPath.row] as? Task
            let context = appDelegate.persistentContainer.viewContext
            context.delete(task!)
            do {
                try context.save()
            } catch {
                fatalError("Error Saving Context \(error)")
            }
            loadDataFromDatabase(date: dateCurrent!)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
    }
    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier ==  "editFromCalendar" {
            let taskController = segue.destination as? AssignmentsViewController
            let selectedRow = tabel.indexPath(for: sender as! UITableViewCell)?.row
            let selectedTask = tasks[selectedRow!] as? Task
            taskController?.currentEvent = selectedTask
        }
    }
    

}
