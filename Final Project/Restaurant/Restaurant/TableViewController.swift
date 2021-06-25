//
//  TableViewController.swift
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

class TableViewController: UIViewController {

    @IBOutlet weak var table1: UIButton!
    @IBOutlet weak var table2: UIButton!
    @IBOutlet weak var table3: UIButton!
    @IBOutlet weak var table4: UIButton!
    @IBOutlet weak var table5: UIButton!
    @IBOutlet weak var table6: UIButton!
    @IBOutlet weak var dateView: UILabel!
    
    let tables = ["table1", "table2", "table3", "table4", "table5", "table6"]
    var tableData: [Table] = []
    let ref = Database.database().reference()
    let settings = UserDefaults.standard
    
    /**
     This function is called when the view is loaded
     */
    override func viewDidLoad() {
        
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        let date = Date()
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        formatter.setLocalizedDateFormatFromTemplate("MM/dd/yyyy")
        dateView?.text = formatter.string(from: date)
    }
    /**
     This function is called when the view appears and loads from the database
     */
    override func viewDidAppear(_ animated: Bool) {
        loadFromDatabase()
    }
    /**
     This function loads the table data from the database
     */
    func loadFromDatabase() {
        tableData.removeAll()
        for table in tables{
            let query = "currentTable/\(table)"
            ref.child(query).observeSingleEvent(of: .value) { [self] (snapshot) in
                let value = snapshot.value as? NSDictionary
                if !snapshot.exists() {return}
                
                if let isUse = value?["inUse"] as? Int {
                    if isUse == 1 {
                        if table == "table1"{
                            table1.setImage(UIImage(named: "T1_taken"), for: .normal)
                        }
                        else if table == "table2" {
                            table2.setImage(UIImage(named: "T2_taken"), for: .normal)
                        }
                        else if table == "table3" {
                            table3.setImage(UIImage(named: "T3_taken"), for: .normal)
                        }
                        else if table == "table4" {
                            table4.setImage(UIImage(named: "T4_taken"), for: .normal)
                        }
                        else if table == "table5" {
                            table5.setImage(UIImage(named: "T5_taken"), for: .normal)
                        }
                        else if table == "table6" {
                            table6.setImage(UIImage(named: "T6_taken"), for: .normal)
                        }
                    }
                    else{
                        if table == "table1"{
                            table1.setImage(UIImage(named: "T1_avalible"), for: .normal)
                        }
                        else if table == "table2" {
                            table2.setImage(UIImage(named: "T2_avalible"), for: .normal)
                        }
                        else if table == "table3" {
                            table3.setImage(UIImage(named: "T3_avalible"), for: .normal)
                        }
                        else if table == "table4" {
                            table4.setImage(UIImage(named: "T4_avalible"), for: .normal)
                        }
                        else if table == "table5" {
                            table5.setImage(UIImage(named: "T5_avalible"), for: .normal)
                        }
                        else if table == "table6" {
                            table6.setImage(UIImage(named: "T6_avalible"), for: .normal)
                        }
                    }
                }
            }
            
        }
    }
    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    /**
     This function faciliates the navigation and passes the table to the next view
     */
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "table1" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table1"
        }
        else if segue.identifier == "table2" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table2"
        }
        else if segue.identifier == "table3" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table3"
        }
        else if segue.identifier == "table4" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table4"
        }
        else if segue.identifier == "table5" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table5"
        }
        else if segue.identifier == "table6" {
            let entryController = segue.destination as? OrderViewController
            entryController?.entryQuery = "table6"
        }
    }
    

}
