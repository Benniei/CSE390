//
//  ClassTableViewController.swift
//  CoreData Demo
//
//  Created by Bennie Chen on 6/25/21.
//

import UIKit
import CoreData

class ClassTableViewController: UITableViewController {

    var courses:[NSManagedObject] = []
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        loadFromDatabase()
        tableView.reloadData()
    }
    
    func loadFromDatabase(){
        let settings = UserDefaults.standard
        let sortField = settings.string(forKey: Constants.kSortField)?.lowercased()
        let sortAscending = settings.bool(forKey: Constants.kSortDirectionAscending)
        
        let context = appDelegate.persistentContainer.viewContext
        let request = NSFetchRequest<NSManagedObject>(entityName: "Course")
        
        let sortDesciptor = NSSortDescriptor(key: sortField, ascending: sortAscending)
        let sortDescriptorArray = [sortDesciptor]
        
        request.sortDescriptors = sortDescriptorArray
        
        do{
            courses = try context.fetch(request)
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
        return courses.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "courseView", for: indexPath)

        // Configure the cell...
        let course = courses[indexPath.row] as! Course
        cell.textLabel?.text = course.id
        cell.detailTextLabel?.text = course.professor
        return cell
    }

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "tableToView" {
            let courseController = segue.destination as? InputViewController
            let selectedRow = self.tableView.indexPath(for: sender as! UITableViewCell)?.row
            let selectedTask = courses[selectedRow!] as? Course
            courseController?.currentCourse = selectedTask
        }
    }
    

}
