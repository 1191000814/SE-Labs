package mapper;

import pojo.Company;

public interface CompanyMapper{

    Company getInfo();

    int update(Company company);
}
