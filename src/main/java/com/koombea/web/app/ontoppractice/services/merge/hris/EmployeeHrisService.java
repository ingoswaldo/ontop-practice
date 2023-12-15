/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge.hris;

import com.merge.api.resources.hris.employees.EmployeesClient;
import com.merge.api.resources.hris.employees.requests.EmployeesListRequest;
import com.merge.api.resources.hris.types.PaginatedEmployeeList;
import lombok.Getter;

@Getter
public class EmployeeHrisService extends BaseHrisService {

    private final EmployeesClient employee;

    public EmployeeHrisService(String accountToken) {
        super(accountToken);
        this.employee = getClient().employees();
    }

    public PaginatedEmployeeList all() {
        return getEmployee().list(EmployeesListRequest.builder().build());
    }
}
