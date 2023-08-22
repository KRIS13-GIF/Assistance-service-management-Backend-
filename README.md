# Assistance-service-management-Backend-

We intend to create software for the management of assistance services for IT products. The system will have to manage:
- customers;
- repairs;
- system users;
- basic reporting.

The system can be accessed only and exclusively by the personnel of the assistance center who are classified in:

- acceptance
- technicians

The basic business process is as follows:
A customer requests the service center to intervent to repair a computer product.
The acceptance operator acquires the product by creating a repair sheet. The data that will be requested are the following:

- serial device;
- brand
- template
- Description of the problem
- date of purchase
- warranty expiry date
- additional notes
- password
- customer name or company name
- customer surname
- full address
- telephone number
- email
- fiscal Code
- VAT number, if company name
- pec
- SDI code

At the end of the acceptance, the customer is given the number of the file created.
After the acceptance phase, the practice can move on to the processing phase.
The processing phase is taken over by a technician.

The processing phase can end with:
- repair completed: the technician must describe the intervention performed and the amount to be charged in the event of out-of-warranty repairs
- refused repair: the technician must describe the reason for the refusal and any amount to be charged.

The acceptance operator informs the customer that the product is ready for collection.
The customer pays and collects the product.
The customer, at any time, can consult the repair status through a page of the repair center website providing as data the product serial number and the case number issued during the acceptance phase (Only the API is required).

With regard to reporting, extract the data relating to repairs:
- total number of repairs successfully completed over a period of time;
- total number of repairs successfully rejected over a period of time;
- cost of production over a period of time;
- number of repairs processed by each technician over a period of time.

![sabanet](https://github.com/KRIS13-GIF/Assistance-service-management-Backend-/assets/71281629/141a6ccf-f4eb-41e1-b70d-6d64be279427)

