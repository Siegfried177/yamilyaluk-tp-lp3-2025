package py.edu.uc.lp32025.mapeable;

import java.util.Objects;

public class Avatar {
    private final String urlImagen;
    private final String nick;

    public Avatar(String urlImagen, String nick) {
        this.urlImagen = urlImagen;
        this.nick = nick;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "urlImagen='" + urlImagen + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Avatar avatar = (Avatar) o;
        return Objects.equals(urlImagen, avatar.urlImagen) &&
                Objects.equals(nick, avatar.nick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlImagen, nick);
    }
}
