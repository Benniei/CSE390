//
//  ViewController.swift
//  TipCalculator
//
//  Created by Bennie Chen on 6/8/21.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var moneyInput: UITextField!
    @IBOutlet weak var fifteen: UIButton!
    @IBOutlet weak var eighteen: UIButton!
    @IBOutlet weak var twentry: UIButton!
    @IBOutlet weak var tipAmount: UILabel!
    @IBOutlet weak var total: UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }


    @IBAction func tipFifteen(_ sender: Any) {
        let priceString = moneyInput.text
        let price = Double(priceString!)
        
        if let price = price{
            let tip = price * 0.15
            let tipOutput = String(format: "Tip Amount: %.2f", tip)
            tipAmount.text = tipOutput
            let totalOutput = String(format:"Total: %.2f", (tip + price))
            total.text = totalOutput
        }else{
            tipAmount.text = "INVALID ENTRY"
            total.text = ""
        }
    }
    
    
    @IBAction func tipEighteen(_ sender: Any) {
        let priceString = moneyInput.text
        let price = Double(priceString!)
        
        if let price = price{
            let tip = price * 0.18
            let tipOutput = String(format: "Tip Amount: %.2f", tip)
            tipAmount.text = tipOutput
            let totalOutput = String(format:"Total: %.2f", (tip + price))
            total.text = totalOutput
        }else{
            tipAmount.text = "INVALID ENTRY"
            total.text = ""
        }
    }
    
    
    @IBAction func tipTwenty(_ sender: Any) {
        let priceString = moneyInput.text
        let price = Double(priceString!)
        
        if let price = price{
            let tip = price * 0.20
            let tipOutput = String(format: "Tip Amount: %.2f", tip)
            tipAmount.text = tipOutput
            let totalOutput = String(format:"Total: %.2f", (tip + price))
            total.text = totalOutput
        }else{
            tipAmount.text = "INVALID ENTRY"
            total.text = ""
        }
    }
}

