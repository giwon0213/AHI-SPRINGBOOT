package com.jsg.ahispringboot.member.mapper;

import com.jsg.ahispringboot.member.dto.CompanyDto;
import com.jsg.ahispringboot.member.dto.MemberDto;
import com.jsg.ahispringboot.member.entity.CompanyEntity;
import com.jsg.ahispringboot.member.entity.MemberEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
<<<<<<< HEAD
<<<<<<< HEAD
    date = "2024-01-07T22:54:24+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
=======
    date = "2024-01-08T14:31:18+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
>>>>>>> fd357a035814d29041592ef74765ace03a1c16de
=======
    date = "2024-01-08T15:41:22+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (Oracle Corporation)"
=======
    date = "2024-01-08T16:25:12+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
>>>>>>> dev
>>>>>>> 8f2852e3e01b88f87bd2792f1e8499c7652719b1
)
public class MemberTransMapperImpl implements MemberTransMapper {

    @Override
    public MemberEntity dtoToEntity(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId( memberDto.getId() );
        memberEntity.setEmail( memberDto.getEmail() );
        memberEntity.setName( memberDto.getName() );
        memberEntity.setPassword( memberDto.getPassword() );
        memberEntity.setPhoneNumber( memberDto.getPhoneNumber() );

        return memberEntity;
    }

    @Override
    public MemberDto entityToDto(MemberEntity memberEntity) {
        if ( memberEntity == null ) {
            return null;
        }

        MemberDto.MemberDtoBuilder memberDto = MemberDto.builder();

        memberDto.id( memberEntity.getId() );
        memberDto.email( memberEntity.getEmail() );
        memberDto.name( memberEntity.getName() );
        memberDto.password( memberEntity.getPassword() );
        memberDto.phoneNumber( memberEntity.getPhoneNumber() );

        return memberDto.build();
    }

    @Override
    public CompanyEntity cDtoToEntity(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        CompanyEntity.CompanyEntityBuilder companyEntity = CompanyEntity.builder();

        companyEntity.companyId( companyDto.getCompanyId() );
        companyEntity.company( companyDto.getCompany() );
        companyEntity.employeesNumber( companyDto.getEmployeesNumber() );
        companyEntity.companyType( companyDto.getCompanyType() );
        companyEntity.establishmentDate( companyDto.getEstablishmentDate() );
        companyEntity.companyHomepage( companyDto.getCompanyHomepage() );

        return companyEntity.build();
    }

    @Override
    public MemberEntity cDtoToMemberEntity(CompanyDto companyDto) {
        if ( companyDto == null ) {
            return null;
        }

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setEmail( companyDto.getEmail() );
        memberEntity.setName( companyDto.getName() );
        memberEntity.setPassword( companyDto.getPassword() );
        memberEntity.setPhoneNumber( companyDto.getPhoneNumber() );

        return memberEntity;
    }
}
