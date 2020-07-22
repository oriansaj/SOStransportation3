public class Purchase {

    private String fullName;
    private String email;
    private String ticketType;

    public Purchase(String fullName, String email, String ticketType)
    {
        this.email = email;
        this.fullName = fullName;
        this.ticketType = ticketType;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFullname()
    {
        return fullName;
    }

    public String getTicketType()
    {
        return ticketType;
    }

}
