package Portfolio.Missing_Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailForm is a Querydsl query type for EmailForm
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEmailForm extends BeanPath<EmailForm> {

    private static final long serialVersionUID = 33629421L;

    public static final QEmailForm emailForm = new QEmailForm("emailForm");

    public final StringPath first = createString("first");

    public final StringPath last = createString("last");

    public QEmailForm(String variable) {
        super(EmailForm.class, forVariable(variable));
    }

    public QEmailForm(Path<? extends EmailForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailForm(PathMetadata metadata) {
        super(EmailForm.class, metadata);
    }

}

