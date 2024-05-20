package org.unibl.etf.ip.webshop.services;

import jakarta.mail.internet.AddressException;

public interface EmailService {
    void sendEmail(String to, String subject, String body) throws AddressException;
}
