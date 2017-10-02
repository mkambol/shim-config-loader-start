package com.epam.spring.config;

public class LoadCredentials {
    private HttpCredentials httpCredentials;
    private Krb5Credentials krb5Credentials;
    private SshCredentials sshCredentials;

    public LoadCredentials(HttpCredentials httpCredentials, Krb5Credentials krb5Credentials, SshCredentials sshCredentials) {
        this.httpCredentials = httpCredentials;
        this.krb5Credentials = krb5Credentials;
        this.sshCredentials = sshCredentials;
    }

    public HttpCredentials getHttpCredentials() {
        return httpCredentials;
    }

    public void setHttpCredentials(HttpCredentials httpCredentials) {
        this.httpCredentials = httpCredentials;
    }

    public Krb5Credentials getKrb5Credentials() {
        return krb5Credentials;
    }

    public void setKrb5Credentials(Krb5Credentials krb5Credentials) {
        this.krb5Credentials = krb5Credentials;
    }

    public SshCredentials getSshCredentials() {
        return sshCredentials;
    }

    public void setSshCredentials(SshCredentials sshCredentials) {
        this.sshCredentials = sshCredentials;
    }
}
