package br.com.gustavo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static EntityManagerFactory entityManagerFactory;

    public static void main(String args[]) {

        // Leitura do persistence.xml
        entityManagerFactory = Persistence.createEntityManagerFactory("hibernatejpa");

        // Cria um EntityManager
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Lembrete> pessoas = new ArrayList<>();
        Lembrete lembreteSalvo;

        Pessoa joao = new Pessoa("João da Silva");
        // joao.setEndereco(new Endereco("Av Amazonas", 1620));

        try {

            // -- INSERIR

            // Abrir uma nova transação com o banco
            em.getTransaction().begin();

            // Envia a instrução de INSERT.
            lembretes.forEach(lembrete -> em.persist(lembrete));

            // Encerra a transação, gravando os dados no banco
            em.getTransaction().commit();


            // -- ATUALIZAR

            // Usando o método find para pegar id=2
            lembreteSalvo = em.find(Lembrete.class, 2L);

            lembreteSalvo.setTitulo("Comprar café");
            lembreteSalvo.setDescricao("Segunda, 15h20");

            em.getTransaction().begin();

            // Envia a instrução de UPDATE
            em.merge(lembreteSalvo);
            em.getTransaction().commit();


            // -- EXCLUIR

            lembreteSalvo = em.find(Lembrete.class, 3L);
            em.getTransaction().begin();

            // Envia a instrução de DELETE
            em.remove(lembreteSalvo);
            em.getTransaction().commit();


            // -- CONSULTAS

            // Usando JPQL + TypedQuery para buscar todos
            TypedQuery<Lembrete> query = em.createQuery("from Lembrete", Lembrete.class);
            lembretesSalvos = query.getResultList();

            if (lembretesSalvos != null) {
                lembretesSalvos.forEach(System.out::println);
            }

        } catch (Exception e) {

            // Rollback em caso de erro
            em.getTransaction().rollback();
            System.out.println("ERRO: " + e.getMessage());

        } finally {

            // Encerra a conexão
            em.close();
        }

    }
}