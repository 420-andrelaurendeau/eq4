import { ReactNode, forwardRef } from "react";
import { Button } from "react-bootstrap";

interface Props {
  children: ReactNode[];
  onClick: (event: React.MouseEvent<HTMLElement>) => void;
}

const CustomToggle = forwardRef<HTMLButtonElement, Props>(
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
