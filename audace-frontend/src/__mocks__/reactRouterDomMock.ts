const reactRouterDomMock: any = jest.createMockFromModule("react-router-dom");

reactRouterDomMock.useNavigate = jest.fn();

module.exports = reactRouterDomMock;

export {};
