# IndyGo Reservation App

### Notes
This application was submitted for the TechPoint S.O.S. Challenge, Summer 2020, as a solution to the transportation challenge.

### Prerequisites
You will need the following items installed on your system:
* [Android Studio](https://developer.android.com/studio)
* A Java IDE of your choice

Also, you will need to install an emulator through Android Studio, to simulate running the app on a real phone. It must be at least API level 19 (KitKat), although the most recent (30) is preferred.

### Running Instructions
1. Run the Server program in your Java IDE.
2. Run the app through the emulator in Android Studio. It cannot yet be run on a real phone, as the server is currently set up to be run locally.
3. Navigate through the app as you wish, until you get to either the reservation or ticket purchasing page.
4. Input the required information into the app, and press the submit button (either "Reserve Now" or "Buy Now," for reservations and ticket purchases, respectively).
5. If you are not in guest mode, a dialog should come up in the server console prompting you to enter the email address and password for the account the confirmation email will be sent from. Enter it if you know it, or type some gibberish if you do not.
6. If you successfully entered the login information, the email should have been sent, and you can return to using the app. If not, you will be prompted to try again. If you want to try and enter the information again, input any variation of "y" or "yes" and return to step 5. If not, input any variation of "n" or "no" and a confirmation PDF will be downloaded to the device instead.
7. Depending on the previous actions, either a confirmation email will have been sent to the address provided, or a confirmation PDF was saved to the device. Check the provided email or the device's file system if you wish.
8. Return to using the app. If you again wish to reserve a seat or purchase a ticket, return to step 4.

### Testing
Due to the nature of the app, most testing was done visually. We ran the app and made sure everything did what it was intended to do (i.e. buttons sent you to the correct screen). We also made significant use of print statements, to show where something might be failing, or what data was being used. For testing our algorithms, like server capacity, we used exhaustive values (including edge cases) and tracked the state of the system, again with print statements. If we had more time, we would have liked to have written unit tests, but we did what we could.

### Tools
* Java mail API - This allowed for easy integration into our app, and allowed for a convenient way to send confirmations in a way that was also familiar to the user.
* Android PDF functionality - This allowed for an easy way to send confirmations in a way that ensures the user will recieve them. They may not always supply an email, and something can go wrong with the server, but they will always be using the app on a device with a file system.
* Java Socket library - This was a quick and simple way to allow the app and server to connect. Our priority in this project was building a working prototype, not a perfect product ready for a full release, so we used the easiest solution. Obviously, in a complete version, we would use something more secure.

### Potential Bugs
* If the server program is not running, nothing will happen when you try to reserve or purchase a ticket. It must be running locally.
* If the time is improperly entered, the server may be unable to parse it. There must be a colon between the hours and minutes. Additionally, it must be entered in military (24-hr) time. This is currently the only way we differentiate between AM and PM.
* We do not currently implement user authentication. If you press the "Login" button on the main screen, the email address is automatically set to "user@example.com," which is not a real address. The email will bounce back.
* You are technically able to reserve a time in the past. The server will recognize this and immediately delete it, but the user is not notified or warned.

### More Information
* [Android Developers (Android Documentation and basic tutorials)](https://developer.android.com/)
* [Basisasis for our socket code](https://www.geeksforgeeks.org/socket-programming-in-java/)
* [Tutorial for generating a PDF](https://www.blueappsoftware.com/how-to-create-pdf-file-in-android/)
* [Dropdown menu tutorial](https://developer.android.com/guide/topics/ui/controls/spinner#java)
* [Basis for our programatically sending email](https://www.youtube.com/watch?v=_wTXqDppu64)
* [Basis for simultaneous checking for removal and listening for connections](https://www.geeksforgeeks.org/killing-threads-in-java/#:~:text=Modern%20ways%20to%20suspend%2Fstop,will%20be%20set%20to%20true.)
