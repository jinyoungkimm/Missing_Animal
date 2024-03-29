package Portfolio.Missing_Animal.domainEntity.animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnimal is a Querydsl query type for Animal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnimal extends EntityPathBase<Animal> {

    private static final long serialVersionUID = 19142522L;

    public static final QAnimal animal = new QAnimal("animal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Portfolio.Missing_Animal.domainEntity.Register, Portfolio.Missing_Animal.domainEntity.QRegister> registers = this.<Portfolio.Missing_Animal.domainEntity.Register, Portfolio.Missing_Animal.domainEntity.QRegister>createList("registers", Portfolio.Missing_Animal.domainEntity.Register.class, Portfolio.Missing_Animal.domainEntity.QRegister.class, PathInits.DIRECT2);

    public QAnimal(String variable) {
        super(Animal.class, forVariable(variable));
    }

    public QAnimal(Path<? extends Animal> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnimal(PathMetadata metadata) {
        super(Animal.class, metadata);
    }

}

