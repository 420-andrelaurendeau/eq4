import React, { useState, useEffect } from 'react';
import { Contract } from '../../model/contract';
import { useTranslation } from 'react-i18next';
import { Table, Form, Row, Col, Container } from 'react-bootstrap';
import { getContractsByDepartmentId } from '../../services/contractService';


interface Props {
    departmentId: number;
}

const ContractsList = ({ departmentId }: Props) => {
  const [contracts, setContracts] = useState<Contract[]>([]);
  const [filteredContracts, setFilteredContracts] = useState<Contract[]>([]);
  const { t } = useTranslation();
  const [searchText, setSearchText] = useState("");

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
            searchRegex.test(contract.supervisor.lastName) // Add more fields to search by if needed
        );
        setFilteredContracts(filtered);
    } else {
        setFilteredContracts(contracts);
    }
}, [searchText, contracts]);

  return (
    <>
      <Container fluid>
        <Row style={{ padding: "16px 0", display: "flex", justifyContent: "flex-end", alignItems: "center" }}>
          <Col><h3>{t("contractsList.title")}</h3></Col>
          <Col>
            <Form>
              <Form.Group controlId="searchText" style={{ margin: 0 }}>
                <Form.Control
                  type="text"
                  placeholder={t("contractsList.searchPlaceholder")}
                  value={searchText}
                  onChange={(e) => setSearchText(e.target.value)}
                  className="custom-search-input"
                />
              </Form.Group>
            </Form>
          </Col>
        </Row>

        {filteredContracts.length > 0 ? (
          <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
            <Table className="table-custom" striped bordered hover size="sm">
              <thead className="table-custom">
                <tr>
                  <th>{t("contractsList.contractId")}</th>
                  <th>{t("contractsList.startHour")}</th>
                  <th>{t("contractsList.endHour")}</th>
                  {/* Add other headers as needed */}
                </tr>
              </thead>
              <tbody className="table-custom">
                {filteredContracts.map((contract) => (
                  <tr key={contract.id}>
                    <td>{contract.id}</td>
                    <td>{contract.startHour}</td>
                    <td>{contract.endHour}</td>
                    {/* Render additional contract details here if needed */}
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
