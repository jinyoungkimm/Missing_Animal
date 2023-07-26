package Portfolio.Missing_Animal.domainEntity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberAddress is a Querydsl query type for MemberAddress
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMemberAddress extends BeanPath<MemberAddress> {

    private static final long serialVersionUID = -692875002L;

    public static final QMemberAddress memberAddress = new QMemberAddress("memberAddress");

    public final StringPath detailAdr = createString("detailAdr");

    public final StringPath streetAdr = createString("streetAdr");

    public final StringPath zipcode = createString("zipcode");

    public QMemberAddress(String variable) {
        super(MemberAddress.class, forVariable(variable));
    }

    public QMemberAddress(Path<? extends MemberAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberAddress(PathMetadata metadata) {
        super(MemberAddress.class, metadata);
    }

}

