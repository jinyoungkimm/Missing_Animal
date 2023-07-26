package Portfolio.Missing_Animal.domainEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMissingAddress is a Querydsl query type for MissingAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMissingAddress extends EntityPathBase<MissingAddress> {

    private static final long serialVersionUID = 2053860386L;

    public static final QMissingAddress missingAddress = new QMissingAddress("missingAddress");

    public final StringPath cityName = createString("cityName");

    public final StringPath Dong = createString("Dong");

    public final StringPath gu = createString("gu");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath prefecture = createString("prefecture");

    public final ListPath<Register, QRegister> registers = this.<Register, QRegister>createList("registers", Register.class, QRegister.class, PathInits.DIRECT2);

    public final StringPath streetName = createString("streetName");

    public final StringPath streetNumber = createString("streetNumber");

    public final StringPath zipcode = createString("zipcode");

    public QMissingAddress(String variable) {
        super(MissingAddress.class, forVariable(variable));
    }

    public QMissingAddress(Path<? extends MissingAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMissingAddress(PathMetadata metadata) {
        super(MissingAddress.class, metadata);
    }

}

