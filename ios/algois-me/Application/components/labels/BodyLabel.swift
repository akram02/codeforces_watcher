import Foundation
import UIKit

class BodyLabel: UILabel {
    
    public override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }

    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }
    
    private func setupView() {
        textColor = Palette.gray
        font = Font.textBody
    }
}
