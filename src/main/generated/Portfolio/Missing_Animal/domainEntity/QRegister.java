package Portfolio.Missing_Animal.domainEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRegister is a Querydsl query type for Register
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRegister extends EntityPathBase<Register> {

    private static final long serialVersionUID = 1007783447L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRegister register = new QRegister("register");

    public final Portfolio.Missing_Animal.domainEntity.animal.QAnimal animal;

    public final StringPath animalAge = createString("animalAge");

    public final StringPath animalName = createString("animalName");

    public final StringPath animalSex = createString("animalSex");

    public final StringPath animalVariety = createString("animalVariety");

    public final StringPath animalWeight = createString("animalWeight");

    public final StringPath etc = createString("etc");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final QMissingAddress missingAddress;

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final EnumPath<Portfolio.Missing_Animal.enumType.RegisterStatus> registerStatus = createEnum("registerStatus", Portfolio.Missing_Animal.enumType.RegisterStatus.class);

    public final EnumPath<Portfolio.Missing_Animal.enumType.ReportedStatus> reportedStatus = createEnum("reportedStatus", Portfolio.Missing_Animal.enumType.ReportedStatus.class);

    public final ListPath<Report, QReport> reports = this.<Report, QReport>createList("reports", Report.class, QReport.class, PathInits.DIRECT2);

    public QRegister(String variable) {
        this(Register.class, forVariable(variable), INITS);
    }

    public QRegister(Path<? extends Register> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRegister(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRegister(PathMetadata metadata, PathInits inits) {
        this(Register.class, metadata, inits);
    }

    public QRegister(Class<? extends Register> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.animal = inits.isInitialized("animal") ? new Portfolio.Missing_Animal.domainEntity.animal.QAnimal(forProperty("animal")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.missingAddress = inits.isInitialized("missingAddress") ? new QMissingAddress(forProperty("missingAddress")) : null;
    }

}

