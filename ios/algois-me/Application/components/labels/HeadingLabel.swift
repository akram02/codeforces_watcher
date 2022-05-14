import Foundation
import UIKit

class HeadingLabel: UILabel {
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }

    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }
    
    private func setupView() {
        numberOfLines = 1
        textColor = Palette.black
        font = Font.textHeading
    }
}
