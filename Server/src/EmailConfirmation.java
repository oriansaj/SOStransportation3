import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class EmailConfirmation {

    private String senderEmail;
    private String senderPassword;
    public boolean downloadPDF;

    /*
     *  Used by developers/server hosts to login to the team email so a confirmation email may be sent
     */
    public void promptAuthorizedUserEmailCredentials()
    {
        System.out.println("To Send Email Confirmation to Customer, it is necessary to login to the team gmail");
        System.out.print("Gmail Address: ");

        Scanner scan = new Scanner(System.in);
        senderEmail = scan.next();

        System.out.print("Password: ");
        senderPassword = scan.next();
    }

    /*
     *  Configurations for sending email through Gmail's SMTP server
     *  Code adapted from: https://www.youtube.com/watch?v=_wTXqDppu64
     */
    public Session emailSendConfig()
    {
        downloadPDF = false;
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }

        });
        return session;
    }

    /*
     *  Backend ability to attempt to send email confirmation from team gmail, if they used incorrect login credentials
     */
    public void attemptToResendConfirmation(Reservation reservation)
    {
        Scanner scan = new Scanner(System.in);
        String userInput;
        boolean validInput = false;
        boolean retry = false;

         do {
             System.out.println("Do you want to attempt to login and send a user confirmation email? [Y/N]");
             userInput = scan.next();
             if ((userInput.compareToIgnoreCase("yes") == 0) || (userInput.compareToIgnoreCase("y")== 0))
             {
                 validInput = true;
                 retry = true;
             }
             else if ((userInput.compareToIgnoreCase("no") == 0) || (userInput.compareToIgnoreCase("n")== 0))
             {
                 validInput = true;
             }

         } while (!validInput);

         if (retry)
         {
             promptAuthorizedUserEmailCredentials();
             sendReservationConfirmation(reservation);
         }
         else
         {
             System.out.println("Confirmation Email Cancelled - Retry Declined - Ticket Downloading to Device");
             downloadPDF = true;
         }
    }

    /*
     *  Sends confirmation of Reservation to user's email
     */
    public void sendReservationConfirmation(Reservation reservation)
    {
        try {
            MimeMessage message = new MimeMessage(emailSendConfig());

            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress(reservation.getEmail())));
            message.setSubject("IndyGo Reservation Confirmation");
            message.setText("Hi " + reservation.getFirstname() + ",\n" +
                    "\n" +
                    "Your IndyGo bus seat reservation has been booked! We'll save you a seat for the" +
                     reservation.getRoute() + " on " + reservation.getStart() +
                    ". Information on how to track your bus can be found HERE.\n" +
                    "\n" +
                    "If you did not book this seat, our contact information can be found HERE.\n" +
                    "\n" +
                    "Have a question? See if it's answered on our Frequently Asked Questions page.\n" +
                    "\n" +
                    "We look forward to seeing you!\n");

            Transport.send(message);

            System.out.println("Sent!");

        } catch (AuthenticationFailedException loginFail) {
            System.out.println("Authorized User - Login Authentication Failed");
            attemptToResendConfirmation(reservation);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }

    //public void sendTicketPurchaseConfirmation()
    {

    }

}
