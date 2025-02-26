package utils;

public class Constants {
    public static String mailTemplate1 = "<html lang=\"en\"><head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>New Review Submitted</title>\n" +
            "    <style>\n" +
            "        @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600;700&display=swap');\n" +
            "        \n" +
            "        body {\n" +
            "            font-family: 'Montserrat', Arial, sans-serif;\n" +
            "            line-height: 1.6;\n" +
            "            color: #333333;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            background-color: #f5f5f5;\n" +
            "        }\n" +
            "        \n" +
            "        .email-container {\n" +
            "            max-width: 600px;\n" +
            "            margin: 20px auto;\n" +
            "            border-radius: 8px;\n" +
            "            overflow: hidden;\n" +
            "            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "        \n" +
            "        .header {\n" +
            "            background: linear-gradient(135deg, #3a7bd5, #00d2ff);\n" +
            "            padding: 30px 20px;\n" +
            "            text-align: center;\n" +
            "            color: white;\n" +
            "        }\n" +
            "        \n" +
            "        .header img {\n" +
            "            max-width: 180px;\n" +
            "            margin-bottom: 15px;\n" +
            "        }\n" +
            "        \n" +
            "        .header h2 {\n" +
            "            margin: 0;\n" +
            "            font-weight: 600;\n" +
            "            letter-spacing: 0.5px;\n" +
            "            font-size: 24px;\n" +
            "        }\n" +
            "        \n" +
            "        .content {\n" +
            "            padding: 35px;\n" +
            "            background-color: #ffffff;\n" +
            "        }\n" +
            "        \n" +
            "        h3 {\n" +
            "            color: #3a7bd5;\n" +
            "            margin-top: 0;\n" +
            "            font-size: 20px;\n" +
            "            font-weight: 600;\n" +
            "            border-bottom: 2px solid #f0f0f0;\n" +
            "            padding-bottom: 10px;\n" +
            "        }\n" +
            "        \n" +
            "        p {\n" +
            "            margin-bottom: 15px;\n" +
            "            color: #555555;\n" +
            "        }\n" +
            "        \n" +
            "        .details {\n" +
            "            background-color: #f9f9f9;\n" +
            "            padding: 20px;\n" +
            "            margin: 25px 0;\n" +
            "            border-radius: 6px;\n" +
            "            border-left: 4px solid #3a7bd5;\n" +
            "        }\n" +
            "        \n" +
            "        .details p {\n" +
            "            margin: 8px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .details strong {\n" +
            "            color: #444444;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        \n" +
            "        .rating {\n" +
            "            display: inline-block;\n" +
            "            margin-left: 5px;\n" +
            "        }\n" +
            "        \n" +
            "        .star {\n" +
            "            color: #FFD700;\n" +
            "            font-size: 18px;\n" +
            "        }\n" +
            "        \n" +
            "        .btn-container {\n" +
            "            text-align: center;\n" +
            "            margin: 30px 0 15px;\n" +
            "        }\n" +
            "        \n" +
            "        .btn {\n" +
            "            display: inline-block;\n" +
            "            padding: 12px 24px;\n" +
            "            background: linear-gradient(135deg, #3a7bd5, #00d2ff);\n" +
            "            color: #ffffff;\n" +
            "            text-decoration: none;\n" +
            "            border-radius: 50px;\n" +
            "            font-weight: 600;\n" +
            "            letter-spacing: 0.5px;\n" +
            "            transition: transform 0.3s ease, box-shadow 0.3s ease;\n" +
            "            box-shadow: 0 4px 10px rgba(58, 123, 213, 0.3);\n" +
            "        }\n" +
            "        \n" +
            "        .btn:hover {\n" +
            "            transform: translateY(-2px);\n" +
            "            box-shadow: 0 6px 15px rgba(58, 123, 213, 0.4);\n" +
            "        }\n" +
            "        \n" +
            "        .footer {\n" +
            "            padding: 20px;\n" +
            "            text-align: center;\n" +
            "            font-size: 12px;\n" +
            "            color: #888888;\n" +
            "            background-color: #f5f5f5;\n" +
            "            border-top: 1px solid #eeeeee;\n" +
            "        }\n" +
            "        \n" +
            "        .footer p {\n" +
            "            margin: 5px 0;\n" +
            "            font-size: 13px;\n" +
            "            color: #888888;\n" +
            "        }\n" +
            "        \n" +
            "        .footer a {\n" +
            "            color: #3a7bd5;\n" +
            "            text-decoration: none;\n" +
            "        }\n" +
            "        \n" +
            "        .social-links {\n" +
            "            margin: 15px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .social-links a {\n" +
            "            display: inline-block;\n" +
            "            margin: 0 8px;\n" +
            "            width: 32px;\n" +
            "            height: 32px;\n" +
            "            background-color: #3a7bd5;\n" +
            "            border-radius: 50%;\n" +
            "            color: white;\n" +
            "            line-height: 32px;\n" +
            "            font-size: 16px;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        \n" +
            "        @media only screen and (max-width: 600px) {\n" +
            "            .content {\n" +
            "                padding: 25px 15px;\n" +
            "            }\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"email-container\">\n" +
            "        <div class=\"header\">\n" +
            "            <img src=\"https://via.placeholder.com/180x50\" alt=\"Company Logo\">\n" +
            "            <h2>Livelo Notification</h2>\n" +
            "        </div>\n" +
            "        \n" +
            "        <div class=\"content\">\n" +
            "            <h3>New Review Submitted</h3>\n" +
            "            \n" +
            "            <p>Hello,</p>\n" +
            "            \n" +
            "            <p>We're pleased to inform you that a new review has been added to your platform. This feedback provides valuable insights about your product or service.</p>\n" +
            "            \n" +
            "            <div class=\"details\">\n"+
            " <p><strong>Submission Date:</strong> ";
    public static String mailTemplate2 = "</p>\n" +
                    "                <p><strong>Customer:</strong> ";

    public static String mailTemplate3 = "</p>\n" +
                    "                \n" +
                    "                \n" +
                    "            </div>\n" +
                    "            \n" +
                    "            <p>Please log in to your dashboard to review this feedback and respond if necessary. Timely responses to customer reviews can significantly improve customer satisfaction and loyalty.</p>\n" +
                    "            \n" +
                    "            \n" +
                    "        </div>\n" +
                    "        \n" +
                    "        <div class=\"footer\">\n" +
                    "            <div class=\"social-links\">\n" +
                    "                <a href=\"#\">f</a>\n" +
                    "                <a href=\"#\">in</a>\n" +
                    "                <a href=\"#\">t</a>\n" +
                    "                <a href=\"#\">ig</a>\n" +
                    "            </div>\n" +
                    "            \n" +
                    "            <p>This is an automated email. Please do not reply.</p>\n" +
                    "            <p>© 2025 Livelo. All Rights Reserved.</p>\n" +
                    "            \n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "\n" +
                    "</body></html>";
}
