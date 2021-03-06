package com.cooksys.ftd.assignments.file;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

	/**
	 * Creates a {@link Student} object using the given studentContactFile. The
	 * studentContactFile should be an XML file containing the marshaled form of
	 * a {@link Contact} object.
	 *
	 * @param studentContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Student} object built using the {@link Contact} data in
	 *         the given file
	 * @throws JAXBException
	 */
	public static Student readStudent(File studentContactFile, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		Contact contact = (Contact) unmarshaller.unmarshal(studentContactFile);

		Student s = new Student(contact);

		return s;

	}

	/**
	 * Creates a list of {@link Student} objects using the given directory of
	 * student contact files.
	 *
	 * @param studentDirectory
	 *            the directory of student contact files to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a list of {@link Student} objects built using the contact files
	 *         in the given directory
	 * @throws JAXBException
	 */
	public static List<Student> readStudents(File studentDirectory, JAXBContext jaxb) throws JAXBException {

		ArrayList<Student> student = new ArrayList<Student>();
		for (File studentFile : studentDirectory.listFiles())
			student.add(readStudent(studentFile, jaxb));

		return student;

	}

	/**
	 * Creates an {@link Instructor} object using the given
	 * instructorContactFile. The instructorContactFile should be an XML file
	 * containing the marshaled form of a {@link Contact} object.
	 *
	 * @param instructorContactFile
	 *            the XML file to use
	 * @param jaxb
	 *            the JAXB context to use
	 * @return an {@link Instructor} object built using the {@link Contact} data
	 *         in the given file
	 * @throws JAXBException
	 */
	public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unmarshaller = jaxb.createUnmarshaller();
		Contact contact = new Contact();
		contact = (Contact) unmarshaller.unmarshal(instructorContactFile);

		Instructor i = new Instructor(contact);

		return i;

	}

	/**
	 * Creates a {@link Session} object using the given rootDirectory. A
	 * {@link Session} root directory is named after the location of the
	 * {@link Session}, and contains a directory named after the start date of
	 * the {@link Session}. The start date directory in turn contains a
	 * directory named `students`, which contains contact files for the students
	 * in the session. The start date directory also contains an instructor
	 * contact file named `instructor.xml`.
	 *
	 * @param rootDirectory
	 *            the root directory of the session data, named after the
	 *            session location
	 * @param jaxb
	 *            the JAXB context to use
	 * @return a {@link Session} object built from the data in the given
	 *         directory
	 * @throws JAXBException
	 */
	public static Session readSession(File rootDirectory, JAXBContext jaxb) throws JAXBException {

		Unmarshaller unmarshaller = jaxb.createUnmarshaller();

		List<Student> studentList = new ArrayList<>();
		Instructor instructor = new Instructor();
		String location = null;
		String startDate = null;

		if (rootDirectory.isDirectory()) {
			for (File f : rootDirectory.listFiles()) {
				location = rootDirectory.getName();
				if (f.isDirectory()) {
					
					for (File fTwo : f.listFiles()) {
						startDate = fTwo.getName();
						if (fTwo.isDirectory()) {
							for (File studentFile : fTwo.listFiles()) {
								System.out.println("Student: " + studentFile.getPath());
								studentList.add(readStudent(studentFile, jaxb));
							}
						} else {
							System.out.println("Instructor: " + fTwo.getPath());
							instructor = readInstructor(fTwo, jaxb);
						}

					}
				}
			}
		}
		Session session = new Session(location, startDate, instructor, studentList);

		return session;
	}

	/**
	 * Writes a given session to a given XML file
	 *
	 * @param session
	 *            the session to write to the given file
	 * @param sessionFile
	 *            the file to which the session is to be written
	 * @param jaxb
	 *            the JAXB context to use
	 * @throws JAXBException
	 */
	public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) throws JAXBException {

		Marshaller marshaller = jaxb.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(session, sessionFile);
	}

	/**
	 * Main Method Execution Steps: 1. Configure JAXB for the classes in the
	 * com.cooksys.serialization.assignment.model package 2. Read a session
	 * object from the <project-root>/input/memphis/ directory using the methods
	 * defined above 3. Write the session object to the
	 * <project-root>/output/session.xml file.
	 *
	 * JAXB Annotations and Configuration: You will have to add JAXB annotations
	 * to the classes in the com.cooksys.serialization.assignment.model package
	 *
	 * Check the XML files in the <project-root>/input/ directory to determine
	 * how to configure the {@link Contact} JAXB annotations
	 *
	 * The {@link Session} object should marshal to look like the following:
	 * <session location="..." start-date="..."> <instructor>
	 * <contact>...</contact> </instructor> <students> ... <student>
	 * <contact>...</contact> </student> ... </students> </session>
	 * 
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException {

		File iF = new File(
				"C:/Users/ftd-11/Downloads/combined-assignments-master/combined-assignments-master/4-file-io-serialization/input/memphis/");

		JAXBContext context = JAXBContext.newInstance(Session.class, Contact.class, Instructor.class, Student.class);
		Session newSession = new Session();
		newSession = readSession(iF, context);

		File oF = new File(
				"C:/Users/ftd-11/Downloads/combined-assignments-master/combined-assignments-master/4-file-io-serialization/output/session.xml");

		writeSession(newSession, oF, context);

	}
}
