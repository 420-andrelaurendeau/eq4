import { ReactNode } from "react";
import { useTranslation } from "react-i18next";
import { Table, Alert } from "react-bootstrap";

interface Props<T> {
  list: T[];
  error: string;
  children: ReactNode;
  emptyListMessage: string;
  title: string;
}

const GenericTable = <T,>({
  list,
  error,
  children,
  emptyListMessage,
  title,
}: Props<T>) => {
  const { t } = useTranslation();

  return (
    <>
      <h5 className="mb-3">{t(title)}</h5>
      {error !== "" ? (
        <Alert className="text-center">{error}</Alert>
      ) : list.length > 0 ? (
        <Table striped bordered >
          {children}
        </Table>
      ) : (
        <p className="text-center">{t(emptyListMessage)}</p>
      )}
    </>
  );
};

export default GenericTable;
