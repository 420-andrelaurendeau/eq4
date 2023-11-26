const reactI18nextMock: any = jest.createMockFromModule("react-i18next");

reactI18nextMock.useTranslation = jest.fn(() => ({
  t: (key: string) => key,
  i18n: {
    changeLanguage: jest.fn(),
  },
}));

reactI18nextMock.initReactI18next = {
  type: "3rdParty",
  init: () => {},
};

module.exports = reactI18nextMock;

export {};
