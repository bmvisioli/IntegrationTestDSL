package model

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
		def server = new Server(port)
		server.with {
			setHandler(new AbstractHandler() {
						public void handle(String target,
								Request baseRequest,
								HttpServletRequest req,
								HttpServletResponse resp) {
							if(target == path) {
								resp.setContentType("text/xml;charset=utf-8")
								resp.setStatus(HttpServletResponse.SC_OK)
								baseRequest.setHandled(true)
								result = baseRequest.getReader().getText()
								resp.getWriter().println(response)
							} else {
								resp.setStatus(404)
							}
							Thread.start {
								try {
									Thread.sleep(500)
								} catch(any) {
									Thread.currentThread().interrupt()
								}
								server.stop()
							}
						}
					})
		}
		server.start()
		server.join()
		return true
	}
}