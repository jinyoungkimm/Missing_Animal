package Portfolio.Missing_Animal.domainEntity.animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDog is a Querydsl query type for Dog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDog extends EntityPathBase<Dog> {

    private static final long serialVersionUID = -2001363842L;

    public static final QDog dog = new QDog("dog");

    public final QAnimal _super = new QAnimal(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ListPath<Portfolio.Missing_Animal.domainEntity.Register, Portfolio.Missing_Animal.domainEntity.QRegister> registers = _super.registers;

    public QDog(String variable) {
        super(Dog.class, forVariable(variable));
    }

    public QDog(Path<? extends Dog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDog(PathMetadata metadata) {
        super(Dog.class, metadata);
    }

}

