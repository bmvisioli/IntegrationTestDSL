package model

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.eclipse.jetty.server.Request
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.AbstractHandler

class MockResponseTestStep extends AbstractTestStep {

	String path
	int port
	String response
	
	@Override
	public boolean run() {
		def received = false
		new Server(port).with {
			setHandler(new AbstractHandler() {
				public void handle(String target,
									Request baseRequest,
									HttpServletRequest req,
									HttpServletResponse resp)
				{
					resp.with {
						setContentType("text/xml;charset=utf-8")
						setStatus(HttpServletResponse.SC_OK)
						getWriter().println(resp)
					}
					baseRequest.setHandled(true)
					result = baseRequest.getReader().getText()
					received = true
					server.stop()
				}
				
			})
			start()
			join()
		}
		while(!received) {}
		return true
	}

}