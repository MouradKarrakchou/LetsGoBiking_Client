
package com.soap.ws.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour DataContainer complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="DataContainer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="exception" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="itinary" type="{http://schemas.datacontract.org/2004/07/RoutingServer}ArrayOfItinary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataContainer", propOrder = {
    "exception",
    "itinary"
})
public class DataContainer {

    @XmlElementRef(name = "exception", namespace = "http://schemas.datacontract.org/2004/07/RoutingServer", type = JAXBElement.class, required = false)
    protected JAXBElement<String> exception;
    @XmlElementRef(name = "itinary", namespace = "http://schemas.datacontract.org/2004/07/RoutingServer", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfItinary> itinary;

    /**
     * Obtient la valeur de la propriété exception.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getException() {
        return exception;
    }

    /**
     * Définit la valeur de la propriété exception.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setException(JAXBElement<String> value) {
        this.exception = value;
    }

    /**
     * Obtient la valeur de la propriété itinary.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfItinary }{@code >}
     *     
     */
    public JAXBElement<ArrayOfItinary> getItinary() {
        return itinary;
    }

    /**
     * Définit la valeur de la propriété itinary.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfItinary }{@code >}
     *     
     */
    public void setItinary(JAXBElement<ArrayOfItinary> value) {
        this.itinary = value;
    }

}
