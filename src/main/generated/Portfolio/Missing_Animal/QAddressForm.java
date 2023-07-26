package Portfolio.Missing_Animal;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressForm is a Querydsl query type for AddressForm
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddressForm extends BeanPath<AddressForm> {

    private static final long serialVersionUID = -629235195L;

    public static final QAddressForm addressForm = new QAddressForm("addressForm");

    public final StringPath detailAdr = createString("detailAdr");

    public final StringPath streetAdr = createString("streetAdr");

    public final StringPath zipcode = createString("zipcode");

    public QAddressForm(String variable) {
        super(AddressForm.class, forVariable(variable));
    }

    public QAddressForm(Path<? extends AddressForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressForm(PathMetadata metadata) {
        super(AddressForm.class, metadata);
    }

}

