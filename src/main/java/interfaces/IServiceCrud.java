package interfaces;

import java.util.List;

public interface IServiceCrud<T> {
    // Ajouter un élément
    void add(T t);


    // Lire tous les éléments
    List<T> getAll();

    // Mettre à jour un élément
    void update(T t);

    // Supprimer un élément
    void delete(int id);

    T getById(int id);

    List<T> search(String criteria);


}
