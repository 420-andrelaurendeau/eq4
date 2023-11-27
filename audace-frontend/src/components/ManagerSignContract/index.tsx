import { useState, useEffect } from 'react';
import { Contract } from '../../model/contract';
import { useTranslation } from 'react-i18next';
import { Table, Row, Col, Container, Button } from 'react-bootstrap';
import { getContractsByDepartmentId } from '../../services/contractService';
import { useNavigate } from 'react-router-dom';
import { format } from 'date-fns';

interface Props {
  departmentId: number;
}

const ContractsList = ({ departmentId }: Props) => {
  const [contracts, setContracts] = useState<Contract[]>([]);
  const [filteredContracts, setFilteredContracts] = useState<Contract[]>([]);
  const { t } = useTranslation();
  const [searchText, setSearchText] = useState<string>("");
  const navigate = useNavigate();

  useEffect(() => {
    getContractsByDepartmentId(departmentId)
      .then((res) => {
        setContracts(res.data);
        setFilteredContracts(res.data);
      })
      .catch((error) => {
        console.error("There was an error fetching the contracts:", error);
      });
  }, [departmentId]);

  useEffect(() => {
    if (searchText) {
      const searchRegex = new RegExp(searchText, "i");
      const filtered = contracts.filter((contract) =>
        searchRegex.test(contract.supervisor.firstName) ||
        searchRegex.test(contract.supervisor.lastName)
      );
      setFilteredContracts(filtered);
    } else {
      setFilteredContracts(contracts);
    }
  }, [searchText, contracts]);

  const handleViewContract = (contractId: number) => {
    navigate(`/contract/${contractId}`);
  };

  return (
    <>
      <Container fluid>
        <Row style={{ padding: "16px 0", display: "flex", justifyContent: "flex-end", alignItems: "center" }}>
          <Col><h3>{t("contractsList.title")}</h3></Col>
        </Row>

        {filteredContracts.length > 0 ? (
          <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
            <Table className="table-custom" striped bordered hover size="sm">
              <thead className="table-custom">
                <tr>
                  <th>{t("contractsList.studentName")}</th>
                  <th>{t("contractsList.employerName")}</th>
                  <th>{t("contractsList.offerTitle")}</th>
                  <th>{t("contractsList.Dates")}</th>
                  <th>{t("contractsList.actions")}</th>
                </tr>
              </thead>
              <tbody className="table-custom">
                {filteredContracts.map((contract) => (
                  <tr key={contract.id}>

                    <td>{contract.application.cv!.student.firstName} {contract.application.cv!.student.lastName}</td>
                    <td>{`${contract.application.offer!.employer.firstName} ${contract.application.offer!.employer.lastName}`}</td>
                    <td>{contract.application.offer!.title}</td>
                    <td>{format(new Date(contract.application.offer!.internshipStartDate), 'dd/MM/yyyy')}, {format(new Date(contract.application.offer!.internshipEndDate), 'dd/MM/yyyy')}</td>
                    <td>
                      <Button variant="primary" size="sm" onClick={() => handleViewContract(contract.id!)}>
                        {t("contractsList.viewDetails")}
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        ) : (
          <p>{t("contractsList.noContractsFound")}</p>
        )}
      </Container>
    </>
  );
};

export default ContractsList;
