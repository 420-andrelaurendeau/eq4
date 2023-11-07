import { PropsWithChildren, forwardRef } from "react";
import { Button } from "react-bootstrap";

interface Props {
  onClick: (event: React.MouseEvent<HTMLElement>) => void;
}

const CustomToggle = forwardRef<HTMLButtonElement, PropsWithChildren<Props>>(
  ({ children, onClick }, ref) => {
    return (
      <Button
        ref={ref}
        onClick={(e) => {
          e.preventDefault();
          onClick(e);
        }}
        variant="outline-dark"
        className="dropdown-toggle"
      >
        {children}
      </Button>
    );
  }
);

export default CustomToggle;
