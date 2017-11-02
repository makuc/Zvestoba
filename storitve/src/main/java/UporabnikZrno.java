import entitete.Uporabnik;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped

public class UporabnikZrno {
    @PersistenceContext(unitName = "zvestoba-jpa")
    private EntityManager em;

    public List<Uporabnik> getUporabniki() {
        Query q = em.createNamedQuery("Uporabniki.getAll");
        return (List<Uporabnik>)(q.getResultList());

    }

    public Uporabnik getUporabnik(String username){
        Query q = em.createNamedQuery("Uporabniki.getOne");
        q.setParameter(1, username);
        Uporabnik usr = (Uporabnik)q.getSingleResult();
        em.refresh(usr);
        return usr;
    }

    public void deleteUporabnik(String username){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Uporabniki.delete");
        q.setParameter(1, username);
        q.executeUpdate();
        em.getTransaction().commit();

    }
    public void updateUporabnikIme(String username, String ime){
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Uporabniki.updateIme");
        q.setParameter(1, username);
        q.setParameter(2, ime);
        q.executeUpdate();
        em.getTransaction().commit();
    }
    public void createUporabnik(String username, String ime, String priimek, String email) {
        Uporabnik usr = new Uporabnik(
                username,
                ime,
                priimek,
                email
        );
        em.getTransaction().begin();
        em.persist(usr);
        em.getTransaction().commit();
    }
    public void storeUporabnik(Uporabnik usr) {
        em.getTransaction().begin();
        em.persist(usr);
        em.getTransaction().commit();
    }
}
