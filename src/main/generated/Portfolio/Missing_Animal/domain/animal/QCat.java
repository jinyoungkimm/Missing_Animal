package Portfolio.Missing_Animal.domain.animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCat is a Querydsl query type for Cat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCat extends EntityPathBase<Cat> {

    private static final long serialVersionUID = -981200453L;

    public static final QCat cat = new QCat("cat");

    public final QAnimal _super = new QAnimal(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ListPath<Portfolio.Missing_Animal.domain.Register, Portfolio.Missing_Animal.domain.QRegister> registers = _super.registers;

    public QCat(String variable) {
        super(Cat.class, forVariable(variable));
    }

    public QCat(Path<? extends Cat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCat(PathMetadata metadata) {
        super(Cat.class, metadata);
    }

}

