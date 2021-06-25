//
//  Food.swift
//  Restaurant
//
//  Created by Bennie Chen on 6/24/21.
//

import Foundation

class Food {
    var foodName: String = ""
    var foodAmount: Int = 0
    var foodPrice: Double = 0.0
    
    init(foodName: String, foodAmount: Int, foodPrice: Double){
        self.foodName = foodName
        self.foodAmount = foodAmount
        self.foodPrice = foodPrice
    }
}

class Table {
    
    var tableNumber: String = ""
    var count: Int = 0
    var name: String = ""
    var order: [Int] = []
    var phone: String = ""
    var price: Double = 0.0
    var time: Double = 0.0
    
    init(tableNumber: String, name: String, count: Int, phone: String, time: Double, price: Double, order: [Int]){
        self.tableNumber = tableNumber
        self.count = count
        self.name = name
        self.order = order
        self.phone = phone
        self.price = price
        self.time = time
    }
}
