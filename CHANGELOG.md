## [1.1.1](https://github.com/SmartOperatingBlock/building-management-microservice/compare/1.1.0...1.1.1) (2023-03-11)


### Dependency updates

* **deps:** update dependency com.azure:azure-digitaltwins-core to v1.3.7 ([bb1a350](https://github.com/SmartOperatingBlock/building-management-microservice/commit/bb1a3500e7ba32f6be5d076b4aa1c7b7f8c2dbb8))
* **deps:** update dependency com.azure:azure-identity to v1.8.1 ([1b0af46](https://github.com/SmartOperatingBlock/building-management-microservice/commit/1b0af461796532348be349c07f187616aed46596))
* **deps:** update node.js to 18.15 ([b9562ff](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b9562ff1619d0f7402a9e5e14570b3c09f2110ae))
* **deps:** update plugin com.gradle.enterprise to v3.12.4 ([4434597](https://github.com/SmartOperatingBlock/building-management-microservice/commit/44345972ad954bba3ab60a9ca88ba749a50868a8))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v1.1.5 ([9adc235](https://github.com/SmartOperatingBlock/building-management-microservice/commit/9adc235633d9f63aa9cff49f54418638415f1bb3))


### Documentation

* **deps:** update plugin dokka to v1.8.10 ([79cf5ca](https://github.com/SmartOperatingBlock/building-management-microservice/commit/79cf5cad53867ba59be52b674890df94954cf9af))

## [1.1.0](https://github.com/SmartOperatingBlock/building-management-microservice/compare/1.0.1...1.1.0) (2023-03-07)


### Features

* add database manager to room controller ([2037896](https://github.com/SmartOperatingBlock/building-management-microservice/commit/20378966acc5cfa05c28ce9cb001cc4b619d6fe9))
* add database mapping of medical technology ([21209c6](https://github.com/SmartOperatingBlock/building-management-microservice/commit/21209c6d8c75e5378ffb58a32affb756ac1d1fff))
* add deletion of medical technology ([2c524c1](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2c524c1545051eea6aaa840f8a43c1ecca61417b))
* add medical technology controller and create and findBy functionalities ([3ac5cc2](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3ac5cc20669574a5d0bc53728dcaf824f2fb9829))
* add medical technology create and get application service ([2669043](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2669043c4e73d8d205f6bbc679b336204dc08e70))
* add medical technology mapping to room application service ([5664520](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5664520f1668f83fc2d2c8c190b2b56036b1b6c9))
* add the possibility to get all the rooms ([f89d804](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f89d804d4b880e9bb90153530267fb38da6b8bed))
* handle get request on room based on datetime ([ec9e8e5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ec9e8e5db0d99540803492103187607b68f26ce7))
* implement creation and deletion of medical technology digital twin ([b3810e8](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b3810e8bc3a7fb63c8fd3d2b26ff30030a7c897b))
* implement database manager create and get of a medical technology ([5b0c38a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5b0c38a07645bb7350c3b4e5f3be239309d707a2))
* implement digital twin medical technology mapping ([c41b2d3](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c41b2d372609578e194f1a80719cea9f67a8b517))
* implement endpoint to create and get a medical technology ([80ebcf0](https://github.com/SmartOperatingBlock/building-management-microservice/commit/80ebcf09d5ef910040fcde06833838ee259e1c93))
* implement find medical technology via digital twins ([886fd87](https://github.com/SmartOperatingBlock/building-management-microservice/commit/886fd87e1f560a49f6f8ba6dae4d2c169dc22fc1))
* implementation of create delete and find room through database ([61a3759](https://github.com/SmartOperatingBlock/building-management-microservice/commit/61a3759ca6a6346f51ec576b3c92db202f14b436))
* update find medical technology to new digital twin model ([764707d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/764707d762643da44a7f223ec10b4c240e4a7fa3))


### Bug Fixes

* check nullability of properties and add util method ([048cfcc](https://github.com/SmartOperatingBlock/building-management-microservice/commit/048cfcc99664889b4faa008e89a6c69ffbe53822))
* correct enum presentation in digital twins ([9b22cb7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/9b22cb77ca5050d2e992c0057816ca65a76d0709))
* due to ktor update now ktor can't serialize in respond with type Any ([35f2708](https://github.com/SmartOperatingBlock/building-management-microservice/commit/35f2708fa2cb66d9fde0ed880cdc566b4b386bb9))
* obtain also roomId from time series data when find historical data ([30aa036](https://github.com/SmartOperatingBlock/building-management-microservice/commit/30aa0366810f69107cb629a6fca634de1cb3ef6c))
* trim merge patch file content in medical technology mapping api ([d3e551f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d3e551fa1da559476893f1a1af527cf87bbccc42))


### Style improvements

* alphabetic order in dependencies ([60f9abc](https://github.com/SmartOperatingBlock/building-management-microservice/commit/60f9abc7c6e0be74fe6ccc5b169f46463db67799))
* disambiguate lambda parameter ([7c7ab07](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7c7ab073dd94a2a5eca30e86795a687c2ca5a83e))


### General maintenance

* add api responses utils to model rest api responses ([ba59144](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ba59144a1dd176718ad199b3f086421796c038b7))
* add azure digital twin query builder ([1a49902](https://github.com/SmartOperatingBlock/building-management-microservice/commit/1a499029f187159d7bd110049253907edc21f4bd))
* add comment to find ([500c393](https://github.com/SmartOperatingBlock/building-management-microservice/commit/500c393e30f8e7727a1d5a34de9a37321fbcc44c))
* add database manager to provider ([be573fe](https://github.com/SmartOperatingBlock/building-management-microservice/commit/be573fe537bfacbee6f0f6a45e8cd447e63cb627))
* add exception handler in order to handle request and responses safely ([3e49248](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3e49248094b4afb0e9038524e79e8dc4928a540b))
* add find on managers ([7a6633a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7a6633a40a0135106efd11712abb5c6c0cd26b67))
* add get all rooms method to room database manager interface ([f24938e](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f24938e36a9a4dc9c247d46e36a4dca539b577ca))
* add historical query on medical technology repository ([b1c2157](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b1c21571bf4ada068f455d2ba95aec4c49e7f27f))
* add idea uiDesigner to gitignore ([f3692d9](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f3692d9c6e2419db5245c693407d1100cdb979ed))
* add information about relationships ([b4a06cf](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b4a06cf993e16edf2e812ec20fe4d27cf91801b1))
* add medical technology database manager ([c15a9d7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c15a9d71afe5f04e5690eac7aa024d3fe87f5e53))
* add medical technology digital twin manager ([ffeb276](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ffeb276fb87fefa15aad7d8797621121a2ef11e4))
* add medical technology merge patch document ([2a17f35](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2a17f35a58bd7e6a4d119ae84a5c52f49c56b3c0))
* add medical technology presentation for digital twins ([646612b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/646612b80691f1c37bd3d0a7976e87f4932a2fb5))
* add method to update medical technology historical data about usage ([d3d01fd](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d3d01fd51b3ea0f1619222638be130e155cc0094))
* add method to update room historical data about environment conditions ([e1be0cd](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e1be0cd0cb75d43656d3e4fe77f6a88b04b69989))
* add No Content http status code when no results are available ([ce45872](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ce4587281cc07392f66e054ad0548518edf1ad66))
* add room entry class ([88b6733](https://github.com/SmartOperatingBlock/building-management-microservice/commit/88b6733a288eceec1b31688b1c20388ed2d495d1))
* add serializable in order to use kotlinx.serialization ([0537dae](https://github.com/SmartOperatingBlock/building-management-microservice/commit/0537daee6ef4462fa7ef7913f6f15cb5ed01faaf))
* add serializer for room to room entry ([e6b57b0](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e6b57b0ab6dfe9706f6afd0c934b097363189257))
* add the possibility to delete also the medical technology mapping to room with the same method ([b35505f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b35505f75c35f33c1f7f3ff4d1284e22d8961f96))
* add time series data description ([2019655](https://github.com/SmartOperatingBlock/building-management-microservice/commit/20196550e9837788b75b86c40581d277feb09f38))
* add update room environmental data ([5ace0e0](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5ace0e0067539a84b4584dfc841ce08ca50af70a))
* allow serialization on medical technology ([8688b5b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8688b5b078ae76dc878abb3ce22d02b813a01eef))
* allow to delete outgoing relationships filtered by their name ([13c4ed8](https://github.com/SmartOperatingBlock/building-management-microservice/commit/13c4ed830e9634c17387b69ecd34a9d4cdd73222))
* base extension of medical technology digital twin manager interface ([2631ca5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2631ca54b33490e4f9b8532b728f1eb4a1eabbaa))
* check on presence of mongo connection string ([724b603](https://github.com/SmartOperatingBlock/building-management-microservice/commit/724b6036a4b9ed8572b16b4885f1756eb6811100))
* comment out service test because need to set up testcontainer ([d3047b7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d3047b7b98efe0a51ea549cea5e0e67cadb942c9))
* create medical technology api dto ([ee6bfe5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ee6bfe571b1e451ef1ebbb144f209a5003c33d38))
* create medical technology api dto serializer and deserializer ([f6a7232](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f6a723241be0079c024e39b29d9fa6b19f40c74f))
* create room database manager interface ([e3f0efd](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e3f0efd4cde8f0a60e47cea968c39175ec10b795))
* create time series medical technology usage data model ([8f33f0f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8f33f0f9f72db9237cb952ca7bcba3268de6b115))
* delete comments ([cfe5d82](https://github.com/SmartOperatingBlock/building-management-microservice/commit/cfe5d82ba282143a047f52a3c52ab793d483b8ab))
* **deserializer:** add deserialization of environmental data ([109e61d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/109e61d7c806ef3740e2d20260c0a6a1bf85cedf))
* enable debug ([ef8ac72](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ef8ac7233e8388fc523ec65964d5c3e094884b71))
* ignore run configurations ([0473368](https://github.com/SmartOperatingBlock/building-management-microservice/commit/0473368a73fe3afa8575cd1f5c1b84dc4d0dc2df))
* implement controllers for deletion mapping and update of a medical technology ([6141a6b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/6141a6b701c6d1ea04229e1804526ea6ba7d35f7))
* implement medical technology mapping to room rest api ([3b0f6f2](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3b0f6f2ccfce040c676464ad640977529f7d34ef))
* include medical technology managers in the provider ([e90930c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e90930c66e3d1a6571d4756a4826eafbd26b448b))
* make deletion of medical technology return boolean ([5c9c885](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5c9c885f2c208b7fd5755f5f5ce4e982263caa8d))
* make room models public ([b50b5f2](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b50b5f2a1fcd28e6323c1ca0d3e2c9dcab4ceeb5))
* pass database manager to controller ([b9bb069](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b9bb0691813321279ee1b15bc1a709506511aa23))
* update README with mongodb connection string requirement ([41523a4](https://github.com/SmartOperatingBlock/building-management-microservice/commit/41523a4791309ea6a290a129b522a6e9f8c00998))
* update README with the description of the azure dt endpoint ([0f3dc3a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/0f3dc3a0f29c009f8e286c13a4804e1c49b14cd5))
* use Instant instead of Date ([821dc9a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/821dc9ac9220c75bd5cf94a24fbf3d59a310e306))


### Dependency updates

* **deps:** add kmongo dependency ([8ac8cff](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8ac8cffa5bf93bdd4b0ac7b4e6a339c21f5e5b77))
* **deps:** add ktor server status pages dependency ([028bd4b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/028bd4b9cb1aef89146380e41b732b8f528889f7))
* **deps:** add ktor test dependencies ([f273c52](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f273c522cc62034ec1932399f2d15b8957353e1a))
* **deps:** add test container dependency ([c0ba373](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c0ba37321daba7ad28a2be327b452a8c1c048b9b))
* **deps:** change test mongo dependency to embedmongo ([86ccaba](https://github.com/SmartOperatingBlock/building-management-microservice/commit/86ccaba565e362eb43871a63cc536a894ccdf0b5))
* **deps:** update dependency gradle to v8.0.2 ([c5b126e](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c5b126e310eee1df9d29fd6e2cdda5b840c90822))
* **deps:** update ktor to v2.2.4 ([550382d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/550382d70fada6dcb5b6fb1098e798d285f224db))
* **deps:** update plugin kotlin-qa to v0.34.2 ([4ec0698](https://github.com/SmartOperatingBlock/building-management-microservice/commit/4ec0698d9680ebd506dcff7fd14c0ba6e14f4ffe))
* **deps:** update plugin kotlin-qa to v0.35.0 ([9908839](https://github.com/SmartOperatingBlock/building-management-microservice/commit/990883946a5ac2d090716a1a6d5de45320b86ae1))
* **deps:** update plugin kotlin-qa to v0.36.1 ([344635c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/344635c5c6d39c917b2c0cc7d36cce63461d22bf))
* **deps:** use kotlinx serialization with kmongo ([8523d9e](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8523d9e7e59877b3c6e35634d2342c69a0bc3a97))


### Documentation

* add property documentation for room controller ([42f8ded](https://github.com/SmartOperatingBlock/building-management-microservice/commit/42f8ded903523557dc4ad2a4970dc54c8eda59da))
* correct delete room digital twin method documentation ([10bf92b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/10bf92b381f6d66fbf2f60f37a0f903de250bcac))
* correct typo ([97baf81](https://github.com/SmartOperatingBlock/building-management-microservice/commit/97baf81d42d655d2ca754571c2ab33e509e2cb3a))
* correct typo ([9c71354](https://github.com/SmartOperatingBlock/building-management-microservice/commit/9c71354951f4ec4e6ce6cd17c6557e226a76154f))
* **rest-api:** add medical technology entry schema ([9b095d7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/9b095d767c8750668383a104b940dbd977060167))
* **rest-api:** delete unused http status ([77d7657](https://github.com/SmartOperatingBlock/building-management-microservice/commit/77d765700513b403916b466097406b1f2545cc12))
* **rest-api:** specify the date-time format in query string parameters ([dfeda0c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/dfeda0c0e45a8a9068bf7ba08252eb451be40671))


### Build and continuous integration

* run codecov coverage only on linux ([116ec5a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/116ec5aef1e3ce3ce4f5217d1c073ed7b7d8b824))


### Tests

* add api room creation test ([eb5da64](https://github.com/SmartOperatingBlock/building-management-microservice/commit/eb5da64278b5560f28966fe33f06e6f592e01e92))
* add location header test ([f90ae93](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f90ae93050bfabe43b37e1d29eb4af95f29c09ca))
* add medical technology api tests ([b460c3d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b460c3d47a58b8a27afc32c09046d8752ff2d344))
* add medical technology digital twin manager test double ([6599d71](https://github.com/SmartOperatingBlock/building-management-microservice/commit/6599d71db35fd63b8e532ec528ffa78fbaadb7d5))
* add post test on medical technology api ([207ee20](https://github.com/SmartOperatingBlock/building-management-microservice/commit/207ee20ce6a170fdc7a0c202d31834888e4dbbc2))
* add room api tests ([310caff](https://github.com/SmartOperatingBlock/building-management-microservice/commit/310caff6535758ad07830ea2a8755cd587834317))
* add room services tests ([eb17d90](https://github.com/SmartOperatingBlock/building-management-microservice/commit/eb17d90e1a8a60d0424d5a5833ad9a64fff8649e))
* add test on room serialization and deserialization ([85f48fe](https://github.com/SmartOperatingBlock/building-management-microservice/commit/85f48fe53bf7435112a03d122e5bb6a091e3bd9c))
* add test to implement azure digital twin query ([7467a8c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7467a8cd1d46f03b78a0194753882c34c4bd1085))
* add tests for medical technology services ([f4ef0e2](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f4ef0e2f473bf957a11a95d597683a47c4fdc2b9))
* add tests for room entry and medical technology presentation ([299a92a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/299a92ab303740a60629f4140f1079243a797bf2))
* implement simple room service test in order to try embedmongo ([e0e1e82](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e0e1e8260bec0dabf58e97847ed250f481a2f99e))
* **refactor:** split room and medical technology api test ([ea78c50](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ea78c50d3ce7758136e2f429cc7b3e8f04c021ca))
* update room entry test ([ec9fb4f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ec9fb4fd6cc8d1daa47259004bb5c203312751ab))


### Refactoring

* apply dry in digital twin client writes ([ebdf2b2](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ebdf2b2cd26c4a0c00d1be8d134dc29d8bbad2c5))
* extract rollback functionality ([cd211ee](https://github.com/SmartOperatingBlock/building-management-microservice/commit/cd211eecdb65adbc0ea26494fac65ee078685cc6))
* make dispatcher private ([167ae7c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/167ae7cda8c1ac540aa288e79c025541132863d9))
* move the time series model to presenter layer ([0c7dcda](https://github.com/SmartOperatingBlock/building-management-microservice/commit/0c7dcda359c3dbda15699a070406fa47ec3154d4))
* reduce cognitive complexity in medical technology post and get api ([9104513](https://github.com/SmartOperatingBlock/building-management-microservice/commit/91045135d418757f9b9ee8223fb4fa2c7a63e6ca))
* separate room services ([fe21aed](https://github.com/SmartOperatingBlock/building-management-microservice/commit/fe21aed7832458dc4ccae4d63ded1caa70e5a9cf))
* simplify logic in medical technology creation api ([996b65b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/996b65b8acecdc44f94f148f24e0ba37831fe1fa))
* split api configuration from api route definition ([1421cf8](https://github.com/SmartOperatingBlock/building-management-microservice/commit/1421cf8a14c73345e5350a28dcfb3f1534e261d9))
* use only medical technology entry fields to create a new medical technology via api ([7d2ec1d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7d2ec1d8e58468903c3afb99bc55e5546c988dc0))
* use only room entry fields to create a new room via api ([82de213](https://github.com/SmartOperatingBlock/building-management-microservice/commit/82de2132dbe8d584e67aef32e79bae9b3d3803f6))
* use value from medical technology adt presentation ([f547bea](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f547beac896b10eb6045a1d1adb7b02ee3008c30))

## [1.0.1](https://github.com/SmartOperatingBlock/building-management-microservice/compare/1.0.0...1.0.1) (2023-02-25)


### Dependency updates

* **core-deps:** update dependency org.jetbrains.kotlinx:kotlinx-serialization-json to v1.5.0 ([19894da](https://github.com/SmartOperatingBlock/building-management-microservice/commit/19894daa2cd5a9e85f127317eee1a831636525c8))

## 1.0.0 (2023-02-23)


### Features

* add application service to delete and get a room ([aad6d74](https://github.com/SmartOperatingBlock/building-management-microservice/commit/aad6d747ea596a29164548eee3e362b25136fad6))
* add azure digital twins presentation for room ([2c2c267](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2c2c267daf3bfb24d7ad50b3d41664c600c90320))
* add create room service ([e334afc](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e334afcdae88a254efc3714c3e5ba8cf84f4a675))
* handle creation deletion and get with present information of a room ([8fe9ae9](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8fe9ae9f67ebb5939a9ec6300a865ec81b833d23))
* implement create of room digital twin ([26ee7cd](https://github.com/SmartOperatingBlock/building-management-microservice/commit/26ee7cd39995d119b19b50b5552060412f00c008))
* implement delete and find of room digital twin ([669540c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/669540c6235fd007ab11969aad5d9ea64f161316))
* implement repository to delete and get a room ([f68e963](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f68e96308a84b96dfca03feaefa05b3dc0fafefb))


### Build and continuous integration

* **deps:** update gradle/wrapper-validation-action action to v1.0.6 ([aee5678](https://github.com/SmartOperatingBlock/building-management-microservice/commit/aee5678dca6390800326d4a2417e10239028d310))
* **kotlin-docs:** change github pages directory for kotlin documentation ([5e1ee95](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5e1ee9589a65cf5ab7b82ca4d6a2c2a7d5a8eace))
* set application entrypoint ([3d0b78f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3d0b78f2bdd0d37adc6df4cc683b174ad951ed4e))
* trigger generation of openapi documentation on github pages ([c6cbec7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c6cbec748ca2a64732d301edea96b536af157760))
* use release and delivery and documentation ghp actions ([133b5d4](https://github.com/SmartOperatingBlock/building-management-microservice/commit/133b5d4686843221ca8b98bb637f51356b58aead))


### Style improvements

* fix code style ([bebca4f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/bebca4f780730d6db6be289faa9a4f38210be488))
* use correct case for api elements ([302562a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/302562ad64229bf3f34d216e368c8897a0cf76b6))


### Dependency updates

* **deps:** add archunit dependency ([b767293](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b76729383e3edd6d9852b20886266e7af9fe7734))
* **deps:** add azure digital twins and identity dependencies ([d28f45a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d28f45ab159f4ecfca029af355acd9d1f1565229))
* **deps:** add kotlin serialization dependencies ([e885a7d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e885a7d735cb642d8ef832356f47d53a9317d636))
* **deps:** add ktor content negotiation dependencies ([7b70bbf](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7b70bbfff0c3370dcecd9d1eeed07ccf2cd047a9))
* **deps:** add ktor dependencies and configurations ([b406aaf](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b406aaf23b91e680e19bc2d841fa6c537f354e3f))
* **deps:** add ktor json content negotiation dependencies ([3b68f86](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3b68f869260eab19d5b7a7995df9a59b499e3c53))
* **deps:** update dependency gradle to v8.0.1 ([f0c90a6](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f0c90a65bc52de474074d5e6f82683a84fdc9a9c))
* **deps:** update plugin kotlin-qa to v0.34.0 ([2c56054](https://github.com/SmartOperatingBlock/building-management-microservice/commit/2c560543ab6156058112bbfbad8eaadfad93dd80))
* **deps:** update plugin kotlin-qa to v0.34.1 ([6d5336e](https://github.com/SmartOperatingBlock/building-management-microservice/commit/6d5336e51535a75f8e2af15a601091a8c4fd5d6b))
* **deps:** update plugin org.danilopianini.gradle-pre-commit-git-hooks to v1.1.3 ([e3cd505](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e3cd5059296657c0e6e35bf4d5857516ee3df58f))


### General maintenance

* adapt api to new docs ([d54c449](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d54c4491eaed3de6eaa8c21684db1f4a305e4f05))
* add check of pre-existance when creating a new room digital twin ([ef19105](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ef191058452ec58ba0186d0569c59af035c5aa73))
* add digital twin manager implementation ([e8e5d0d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e8e5d0d7ce413ed6f4b7aa51af49df4a8fb83481))
* add environment data concepts ([4eb67ce](https://github.com/SmartOperatingBlock/building-management-microservice/commit/4eb67ce439cf33fb1982a0e0704321f5ea900050))
* add environmental data to room api dto ([6f0346a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/6f0346ad45c76cb4eae15821c58cf135a28d9fab))
* add getRooms to room repository ([79e16ae](https://github.com/SmartOperatingBlock/building-management-microservice/commit/79e16aeaaa17d46a1899280f2ea85d26bd327118))
* add manager provider interface and simple implementation ([5dd58d1](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5dd58d1c406aaf3bb9fb86181280e94c3f2dffe7))
* add medical technology concepts ([ea7bff8](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ea7bff80aecf1a89f430bc2f87b4b9fd5c657ad9))
* add room api dto deserialization ([bd529ac](https://github.com/SmartOperatingBlock/building-management-microservice/commit/bd529ac6b1352a7a6e8b16369b6f525146066ce1))
* add room concepts ([23421d6](https://github.com/SmartOperatingBlock/building-management-microservice/commit/23421d61fe4e89322c60908c82293f7f1e05b308))
* add room controller ([01754ec](https://github.com/SmartOperatingBlock/building-management-microservice/commit/01754ecfdd92676292f4634a8d217ac6ed20a1c1))
* add room digital twin manager interface ([e0a8fd8](https://github.com/SmartOperatingBlock/building-management-microservice/commit/e0a8fd84800c120bf50b29099bb8938b4872c9c0))
* add room environmental data description ([c85df61](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c85df61b1db4b4f555002a09408bc34467f7a260))
* add serializer for api ([f3622e1](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f3622e1064282554336ca1b860674811434e9337))
* add zone concepts ([a508ec9](https://github.com/SmartOperatingBlock/building-management-microservice/commit/a508ec92dc570dcfe9880a73273dc261a9e5da2c))
* call create room service from api ([4703808](https://github.com/SmartOperatingBlock/building-management-microservice/commit/470380855f704b782efabee63c9b302556f394cf))
* change api endpoint with current api version ([441695e](https://github.com/SmartOperatingBlock/building-management-microservice/commit/441695efc88045a164a602f68ecd8117e77f63fb))
* configure ktor content negotiation ([f24e4ef](https://github.com/SmartOperatingBlock/building-management-microservice/commit/f24e4ef215406dd20d42f2837881622c556eb2b2))
* create azure digital twins client connection ([22ae840](https://github.com/SmartOperatingBlock/building-management-microservice/commit/22ae84095bf22c49635537b97c1d1d2b219d32fb))
* create medical technology repository interface ([79ca86f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/79ca86f2f8c018addb74d6e52d95f352d9652015))
* create room api dto ([384eb46](https://github.com/SmartOperatingBlock/building-management-microservice/commit/384eb46281e95186aa675916ef019571e615db7d))
* create room repository interface ([80f6206](https://github.com/SmartOperatingBlock/building-management-microservice/commit/80f620620df8dbe7564ecf5fdb9719dde8395f01))
* create use case interface ([8c9cc16](https://github.com/SmartOperatingBlock/building-management-microservice/commit/8c9cc162b8055d85e9efce7534f8cff90e3737e5))
* delete logs ([3821f37](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3821f377a2846a59061fd46e3a4dde692040918d))
* delete sample test ([18ca20d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/18ca20dbbb68adf4850efcb068b8f49eca5a74e7))
* implement rest-api schema ([3a26561](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3a26561471b87f0375e714f771a9e95ee3e5cb93))
* log less information to improve readability of logs ([7019d60](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7019d6035468ffe792a264097e07c6ba5c22de47))
* **medical-technology:** add isInUse property ([a0b03d7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/a0b03d7efd44aefb2040824b31ab153e6093560b))
* pass provider to app ([6590bec](https://github.com/SmartOperatingBlock/building-management-microservice/commit/6590bec4f6cb5144e90e621b9b1544057697bbd4))
* **rest-api:** add simple rest api controller ([823872d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/823872df4bf3bb83a323540d4197176bb183a2f5))
* **setup:** setup microservice from template ([824292a](https://github.com/SmartOperatingBlock/building-management-microservice/commit/824292a2a77661e87c6d7c413eb8e0e0b417c0c9))
* try room dto api deserialization ([5531461](https://github.com/SmartOperatingBlock/building-management-microservice/commit/5531461634f64d74a8d6546385c6ccd49352af34))
* typo in medical technology routing ([d646d42](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d646d425034c342b78a32da299643a3a7444cef5))
* update README with environment variable requirements ([575045d](https://github.com/SmartOperatingBlock/building-management-microservice/commit/575045d46510cbd660724948081e96b04392e259))


### Documentation

* init rest api docs with components description ([0f13c01](https://github.com/SmartOperatingBlock/building-management-microservice/commit/0f13c01f305656aa461043ccb4483b44176c98d7))
* **rest-api:** add get all rooms api ([1634cde](https://github.com/SmartOperatingBlock/building-management-microservice/commit/1634cdeafa5102be5e7bc61e07d5b06ad8306480))
* **rest-api:** add medical technology api documentation ([85d19b7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/85d19b7b79b1d2647321be88c469d404ade5c3da))
* **rest-api:** add room api documentation ([facc278](https://github.com/SmartOperatingBlock/building-management-microservice/commit/facc2788c52ed21290b2f48c689a92dd56b7d1b6))
* **rest-api:** add tags to documentation to separate apis ([37d4ab5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/37d4ab5893ede9a443803f789b75670ad32cbfa1))
* **rest-api:** consider the case that no content is found ([658e3c7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/658e3c73a50b1b3f363fbe1c18bf884a007ea53c))
* **rest-api:** delete no content status code from get room and correct examples ([842df5c](https://github.com/SmartOperatingBlock/building-management-microservice/commit/842df5c39fe8b333ffac639998519e4fd05af949))
* **rest-api:** modify description of 404 status code on medical technology mapping patch ([a58b52b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/a58b52b143ed56ce1a8840eb606cca264c04cd11))
* **rest-api:** use camelCase for schema properties ([c4cbb0b](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c4cbb0b1a228c364b69b20742f76b27edeb9a01f))
* **typo:** correct typo in documentation ([605ae07](https://github.com/SmartOperatingBlock/building-management-microservice/commit/605ae07ee32a12e45c125ba0a0a59efcfc502f11))


### Refactoring

* change name for digital twin test double ([47adfd4](https://github.com/SmartOperatingBlock/building-management-microservice/commit/47adfd4ac336c5265c5c4696d83e8aebb029d7fb))
* create new room entry schema and refactor room type ([ddcca26](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ddcca264faf0355a7fdc1ce6df9df62cd3bbb9c4))
* make isInUse property immutable ([ad3392f](https://github.com/SmartOperatingBlock/building-management-microservice/commit/ad3392f1e6e0511383025b084e38c711eccbbef9))
* remove environment data module and directly exposes classes ([7a2a034](https://github.com/SmartOperatingBlock/building-management-microservice/commit/7a2a0342b7e2fb75ebc3224406ec35a4a0ab96de))
* set date time in findBy repository method as nullable ([fd0ae75](https://github.com/SmartOperatingBlock/building-management-microservice/commit/fd0ae757e31c2aaa1158aac24d57ca3c688413a3))


### Tests

* add some tests on room application services ([3329eb4](https://github.com/SmartOperatingBlock/building-management-microservice/commit/3329eb41239f27f13f50bfced364e14e327f7ee3))
* **application-service:** test creation of a room ([d3c5ed7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/d3c5ed70d1a3a50c46e6546c7e87e3d92ed3a45f))
* **architecture:** add base test for architecture ([98f21d5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/98f21d531ac17249134dfabfa2550822a4dd1cec))
* **architecture:** avoid import of test classes ([122cbcc](https://github.com/SmartOperatingBlock/building-management-microservice/commit/122cbccb56d146ac6f25be18630c9dd11720b507))
* create digital twin manager stub ([7693718](https://github.com/SmartOperatingBlock/building-management-microservice/commit/769371837b86b81afdbd358f44fc9c697a19adda))
* improve architectural tests ([b76f118](https://github.com/SmartOperatingBlock/building-management-microservice/commit/b76f118680df7f6851ddf824712edc60e57170b9))
* **luminosity:** test luminosity concept ([27795cb](https://github.com/SmartOperatingBlock/building-management-microservice/commit/27795cb4aa21e96b5222d4e80ba65820b43cc9bb))
* **medical-technology:** add tests for medical technology ([c61a9e5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/c61a9e56c4a8ffe3762adce1765ff0a06dd5fecc))
* **room:** test room concepts ([bdb4ad5](https://github.com/SmartOperatingBlock/building-management-microservice/commit/bdb4ad51a104c7bd33c319ae3ad1c48b7631914d))
* **zone:** test zone concepts ([57aa1f7](https://github.com/SmartOperatingBlock/building-management-microservice/commit/57aa1f78fc497317dcaffef18f578b78930c9497))
