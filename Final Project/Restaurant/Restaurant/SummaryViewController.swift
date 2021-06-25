//
//  SummaryViewController.swift
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

class SummaryViewController: UIViewController {
    @IBOutlet weak var totalRevenue: UILabel!
    @IBOutlet weak var numOrders: UILabel!
    /**
     This function is called when the view is loaded
     */
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    /**
     This function is called when the view appears
     */
    override func viewDidAppear(_ animated: Bool) {
        let settings = UserDefaults.standard
        let rev = settings.double(forKey: Constants.koverallTotal)
        let order = settings.integer(forKey: Constants.knumOrders)
        
        totalRevenue?.text = "Total Revenue: $\(String(format: "%.2f", rev))"
        numOrders?.text = "Orders Completed: \(String(format: "%d", order))"
    }
}
