//
//  ViewController.swift
//  CatchMe
//
//  Created by Bennie Chen on 6/10/21.
//

import UIKit

class ViewController: UIViewController {

    
    @IBOutlet var safe: UIView!
    @IBOutlet weak var score: UILabel!
    @IBOutlet weak var sender: UIButton!
    @IBOutlet weak var zone: UIImageView!
    @IBOutlet weak var playAgainButton: UIButton!
    var count = 0
    
    private var regionminX: CGFloat = 0
    private var regionminY: CGFloat = 0
    private var regionmaxX: CGFloat = 0
    private var regionmaxY: CGFloat = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        playAgainButton.isHidden = true
        zone.layer.zPosition = 0
        sender.layer.zPosition = 1
    }

    
    private func moveButton(){
        let minX = safe.safeAreaInsets.left
        let maxX = safe.bounds.width - safe.safeAreaInsets.right
        let minY = safe.safeAreaInsets.top
        let maxY = safe.bounds.height - safe.safeAreaInsets.bottom
        setZone()
        let randomX = CGFloat.random(in: minX...maxX)
        let randomY = CGFloat.random(in: minY...maxY)
        sender.center = .init(x: randomX, y: randomY)
        count += 1
        let output = "Score: \(count)"
        score.text = output
        if(checkInZone(randomX, randomY)){
            score.text = "Congrats\nScore: \(count)"
            sender.isEnabled = false // Disables the button
            playAgainButton.isHidden = false
        }
    }
    
    private func resetButton(){
        let minX = safe.safeAreaInsets.left
        let maxX = safe.bounds.width - safe.safeAreaInsets.right
        let minY = safe.safeAreaInsets.top
        let maxY = safe.bounds.height - safe.safeAreaInsets.bottom
        let setX = (maxX-minX) * 3/4
        let setY = (maxY-minY) * 3/5
        sender.center = .init(x: setX, y: setY)
        count = 0
        sender.isEnabled = true
        playAgainButton.isHidden = true
    }
    
    @IBAction func playAgain(_ sender: Any) {
        resetButton()
    }
    
    private func setZone(){
        let halfWidth = zone.bounds.width/2.0
        let halfHeight = zone.bounds.height/2.0
        regionminX = zone.center.x - halfWidth
        regionminY = zone.center.y - halfHeight - safe.safeAreaInsets.top
        regionmaxX = zone.center.x + halfWidth
        regionmaxY = zone.center.y + halfHeight - safe.safeAreaInsets.top
    }
    
    private func checkInZone(_ x: CGFloat, _ y: CGFloat) -> Bool{
        if x > regionminX && x < regionmaxX && y > regionminY && y < regionmaxY {
            return true;
        }
        else {
            return false;
        }
    }
    
    @IBAction func catchMeButtonTapped(_ sender: Any) {
        moveButton()
    }
}
