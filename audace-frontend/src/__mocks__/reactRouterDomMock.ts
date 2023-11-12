const reactRouterDomMock: any = jest.createMockFromModule("react-router-dom");

reactRouterDomMock.useNavigate = jest.fn();
reactRouterDomMock.useLocation = () => ({
  pathname: "/",
  search: "",
  hash: "",
  state: null,
  key: "",
});

module.exports = reactRouterDomMock;

export {};
