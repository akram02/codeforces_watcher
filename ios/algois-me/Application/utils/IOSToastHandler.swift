import common
import Toast_Swift

class IOSToastHandler: ToastHandler {
    
    func handle(message: Message) {
        var text: String?

        switch (message) {
        case _ as Message.NoConnection:
            text = "check_connection".localized
        case _ as Message.UserAlreadyAdded:
            text = "User already added".localized
        case _ as Message.None:
            text = nil
        case let message as Message.Custom:
            text = message.message
        default:
            break
        }

        if let text = text {
            showToast(message: text)
        }
    }
    
    func showToast(message: String) {
        UIApplication.shared.windows.last?.makeToast(message)
    }
}
