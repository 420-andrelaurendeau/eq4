import { ReactNode } from "react";
import { useTranslation } from "react-i18next";
import { Table } from "react-bootstrap";

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
      <h2 className="mb-3">{t(title)}</h2>
      {error !== "" ? (
        <p className="text-center">{error}</p>
      ) : list.length > 0 ? (
        <Table striped bordered hover size="sm">
          {children}
        </Table>
      ) : (
        <p className="text-center">{t(emptyListMessage)}</p>
      )}
    </>
  );
};

export default GenericTable;
