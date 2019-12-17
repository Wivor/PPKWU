package main;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.FormattedName;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

class Zad4 {
    String vcard(String search, String login, String password) {
        String Url = "https://weeia.edu.p.lodz.pl/auth/mnet/jump.php?hostid=18&wantsurl=/user/users.php";
        String loginUrl = "https://cas.p.lodz.pl/cas/login?service=https%3A%2F%2Fadm.edu.p.lodz.pl%2Flogin%2Findex.php%3FauthCAS%3DCAS";

        StringBuilder html = new StringBuilder();

        try {
            System.out.println("Starting autoLogin on " + loginUrl);
            WebClient client = autoLogin(loginUrl, login, password);
            HtmlPage page = client.getPage(Url);
            page = client.getPage("https://adm.edu.p.lodz.pl/user/users.php?perpage=200&search=" + search);
            page.getWebClient().addRequestHeader("charset", "UTF-8");

            System.out.println(page);
            System.out.println(search);

            html.append("<table>");

            List<HtmlDivision> users = page.getByXPath("//div[@class='user-info']");
            System.out.println("Users: " + users.size());

            int i = 0;
            for (HtmlDivision row : users) {
                User user = new User();

                String fullname = row.getElementsByTagName("h3").get(0).getTextContent();
                String[] name = fullname.split(" ");
                user.name = name[0];
                user.surname = name[1];

                user.title = row.getElementsByTagName("h4").get(0).getTextContent();

//                List<HtmlSpan> content = row.getByXPath("//span[@class='item-content']");
                List<HtmlElement> content = row.getElementsByTagName("li");
                user.email = content.get(0).getChildNodes().item(1).getTextContent();
                user.phone = content.get(1).getChildNodes().item(1).getTextContent();
                user.afinition = content.get(2).getChildNodes().item(3).getTextContent();

                System.out.println("-----");

                VCard vCard = new VCard();

                //.setFamily(user.surname);
//                n.setGiven(user.name);
//                vCard.name.setStructuredName(n);
                FormattedName n = new FormattedName(fullname);
                n.getParameters().setCharset("ISO-8859-1");
                vCard.setFormattedName(n);

                vCard.addTitle(user.title);
                vCard.addEmail(user.email);
                vCard.addTelephoneNumber(user.phone);

                vCard.addNote(user.afinition);

                String str = Ezvcard.write(vCard).version(VCardVersion.V3_0).go();
                System.out.println(str);

                BufferedWriter writer = new BufferedWriter(new FileWriter("vcard/card" + i + ".vcf"));
                writer.write(str);
                writer.close();

                html.append("<tr>")
                    .append("<td>")
                        .append(user.name)
                        .append(" ")
                        .append(user.surname)
                    .append("</td>")
                    .append("<td>")
                        .append("<a href=\"/download/card" + i + ".vcf\" download=\"card" + i + ".vcf\">Get vCard</a>")
                    .append("</td>")
                    .append("</tr>");

                i++;
            }

            html.append("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(html.toString());

        return html.toString();
    }

    public static WebClient autoLogin(String loginUrl, String login, String password) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        WebClient client = new WebClient(BrowserVersion.FIREFOX_52);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page = client.getPage(loginUrl);
        System.out.println(page.getBaseURL());

        HtmlInput inputPassword = page.getFirstByXPath("//input[@type='password']");
        //The first preceding input that is not hidden
        HtmlInput inputLogin = inputPassword.getFirstByXPath("//input[@name='username']");

        inputLogin.setValueAttribute(login);
        inputPassword.setValueAttribute(password);

        //get the enclosing form
        HtmlForm loginForm = inputPassword.getEnclosingForm();

        //submit the form
        page = loginForm.getInputByValue("LOGIN").click();

        //get page out of it
        System.out.println(page.getBaseURL());

        //returns the cookie filled client :)
        return client;
    }

     class User {
        String name;
        String surname;
        String title;
        String email;
        String phone;
        String afinition;
     }
}
