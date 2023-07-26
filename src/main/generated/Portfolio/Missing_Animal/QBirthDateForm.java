package Portfolio.Missing_Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBirthDateForm is a Querydsl query type for BirthDateForm
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBirthDateForm extends BeanPath<BirthDateForm> {

    private static final long serialVersionUID = -1752032418L;

    public static final QBirthDateForm birthDateForm = new QBirthDateForm("birthDateForm");

    public final StringPath day = createString("day");

    public final StringPath month = createString("month");

    public final StringPath year = createString("year");

    public QBirthDateForm(String variable) {
        super(BirthDateForm.class, forVariable(variable));
    }

    public QBirthDateForm(Path<? extends BirthDateForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBirthDateForm(PathMetadata metadata) {
        super(BirthDateForm.class, metadata);
    }

}

