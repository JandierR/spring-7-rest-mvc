CREATE TABLE beer
(
    id               VARCHAR(36)  NOT NULL,
    version          INT NULL,
    beer_name        VARCHAR(50)  NOT NULL,
    beer_style       SMALLINT     NOT NULL,
    upc              VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NULL,
    quantity_on_hand INT NULL,
    price            DECIMAL      NOT NULL,
    created_date     datetime NULL,
    update_date      datetime NULL,
    CONSTRAINT pk_beer PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id                 VARCHAR(36) NOT NULL,
    version            INT NULL,
    customer_name      VARCHAR(255) NULL,
    created_date       datetime NULL,
    last_modified_date datetime NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);