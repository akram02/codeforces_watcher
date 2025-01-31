//
// Created by Yevhenii Kanivets on 11/01/2020.
// Copyright (c) 2020 xorum.io. All rights reserved.
//

import Foundation
import UIKit
import SwiftUI

extension UIColor {

    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")

        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }

    convenience init(rgb: Int) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF
        )
    }
    
    convenience init(
        lightColor: UIColor,
        darkColor: UIColor
    ) {
        self.init(dynamicProvider: {
            $0.userInterfaceStyle == .dark
                ? darkColor : lightColor
        })
    }
    
    var swiftUIColor: SwiftUI.Color {
        Color(self)
    }
    
    func lighter(by amount: CGFloat, alpha: CGFloat = 1) -> UIColor? {
        self.adjust(by: amount, alpha: alpha)
    }

    func darker(by amount: CGFloat, alpha: CGFloat = 1) -> UIColor? {
        self.adjust(by: amount, alpha: alpha)
    }

    func adjust(by amount: CGFloat, alpha: CGFloat = 1) -> UIColor? {
        var red: CGFloat = 0, green: CGFloat = 0, blue: CGFloat = 0
        if self.getRed(&red, green: &green, blue: &blue, alpha: nil) {
            return UIColor(
                red: min(red + amount, 1),
                green: min(green + amount, 1),
                blue: min(blue + amount, 1),
                alpha: alpha
            )
        } else {
            return nil
        }
    }
}

func getColorByUserRank(_ rank: String?) -> UIColor {
    var color = UIColor()
    
    switch (rank) {
    case nil:
        color = Palette.black
    case "newbie", "новичок":
        color = Palette.gray
    case "pupil", "ученик":
        color = Palette.green
    case "specialist", "специалист":
        color = Palette.blueGreen
    case "expert", "эксперт":
        color = Palette.blue
    case "candidate master", "кандидат в мастера":
        color = Palette.purple
    case "master", "мастер", "international master", "международный мастер":
        color = Palette.orange
    case "grandmaster", "international grandmaster", "legendary grandmaster",
         "гроссмейстер", "международный гроссмейстер", "легендарный гроссмейстер":
        color = Palette.red
    default:
        color = Palette.gray
    }
    
    return color
}

func colorTextByUserRank(text: String, rank: String?) -> NSMutableAttributedString {
    let color = getColorByUserRank(rank)

    let attributedText = NSMutableAttributedString(string: text).apply {
        $0.addAttribute(NSAttributedString.Key.foregroundColor, value: color, range: NSRange(location: 0, length: text.count))
    }

    if ["legendary grandmaster", "легендарный гроссмейстер"].contains(rank) {
        attributedText.addAttribute(NSAttributedString.Key.foregroundColor, value: Palette.black, range: NSRange(location: 0, length: 1))
    }

    return attributedText
}
