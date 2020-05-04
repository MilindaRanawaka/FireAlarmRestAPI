import React from 'react';
import logo from './logo.svg';
import './App.css';
import Countdown from "react-countdown";

export default class DataFetch extends React.Component{

  //Declare count down,  Sensor data and loading status
  state = {
    loading: true,
    sensor: null,
    seconds: 40
  };

  //Update countdown
  tick() {
    this.setState(state => ({
      seconds: state.seconds - 1
    }));
  }

  //Get data
  async componentDidMount(){

    //Get data for first loading
    const url = "http://localhost:8080/firealamrest/webresources/sensors";

    const response = await fetch(url);
    const data = await response.json();

    this.setState({
      sensor: data.sensor,
      loading: false,
      seconds: 40
    })

    this.interval = setInterval(() => this.tick(), 1000);

    //Update data every 40 seconds
    try {
      setInterval(async () => {
        const url = "http://localhost:8080/firealamrest/webresources/sensors";

        const response = await fetch(url);
        const data = await response.json();

        this.setState({
          sensor: data.sensor,
          loading: false,
          seconds: 40
        })
        const renderer = ({ seconds }) => {
          return <span>{seconds}</span>;
        };
      }, 40000);
    } catch(e) {
      console.log(e);
    }
  }

  //To update countdown
  componentWillUnmount() {
    clearInterval(this.interval);
  }

  //HTML coding and jsx events
  //Styling using Bootstrap
  render() {
    const renderer = ({ seconds }) => {
      return <span>{seconds}</span>;
    };

    return (
        <div>
          <div className="bubbles">

          </div>

          {this.state.loading ? (
              <div className="spinner-border" role="status">
                <span className="sr-only">Loading...</span>
              </div>
          ) :(
              <div className="container-fluid">
                <h2 className="text-center">Fire Alarm Sensor Monitoring System</h2>
                <h4>Next Update in {this.state.seconds} Seconds</h4>
                <div className="row">
                    {this.state.sensor.map(item => {
                      if (item.sensorStatus==="true") {
                        if (item.co2Level > 5 || item.smokeLevel > 5){
                          return(
                              <div>
                                <div className="card bg-danger mb-3 our-card">
                                  <h5 className="card-header">
                                    Floor No&nbsp;: {item.sensorLocationFloorNo}<br/>
                                    Room No: {item.sensorLocationRoomNo}
                                  </h5>
                                  <div className="card-body">
                                    <p className="card-text">
                                      CO2 Level&nbsp;&nbsp;&nbsp;&nbsp;: {item.co2Level}<br/>
                                      Smoke Level: {item.smokeLevel}
                                    </p>
                                  </div>
                                  <div className="card-footer">
                                    {item.sensorStatus==="true"
                                        ? <h6><i className="fas fa-circle circle-active"></i>Active</h6>
                                        : <h6><i className="fas fa-circle circle-deactivate"></i>Deactivated</h6>
                                    }
                                  </div>
                                </div>
                              </div>
                          )
                        }
                        else {
                          return(
                              <div>
                                <div className="card bg-success mb-3 our-card">
                                  <h5 className="card-header">
                                    Floor No&nbsp;: {item.sensorLocationFloorNo}<br/>
                                    Room No: {item.sensorLocationRoomNo}
                                  </h5>
                                  <div className="card-body">
                                    <p className="card-text">
                                      CO2 Level&nbsp;&nbsp;&nbsp;&nbsp;: {item.co2Level}<br/>
                                      Smoke Level: {item.smokeLevel}
                                    </p>
                                  </div>
                                  <div className="card-footer">
                                    {item.sensorStatus==="true"
                                        ? <h6><i className="fas fa-circle circle-active"></i>Active</h6>
                                        : <h6><i className="fas fa-circle circle-deactivate"></i>Deactivated</h6>
                                    }
                                  </div>
                                </div>
                              </div>
                          )
                        }
                      } else {
                        return(
                            <div>
                              <div className="card bg-secondary mb-3 our-card">
                                <h5 className="card-header">
                                  Floor No&nbsp;: {item.sensorLocationFloorNo}<br/>
                                  Room No: {item.sensorLocationRoomNo}
                                </h5>
                                <div className="card-body">
                                  <p className="card-text">
                                    CO2 Level&nbsp;&nbsp;&nbsp;&nbsp;: {item.co2Level}<br/>
                                    Smoke Level: {item.smokeLevel}
                                  </p>
                                </div>
                                <div className="card-footer">
                                  {item.sensorStatus==="true"
                                      ? <h6><i className="fas fa-circle circle-active"></i>Active</h6>
                                      : <h6><i className="fas fa-circle circle-deactivate"></i>Deactivated</h6>
                                  }
                                </div>
                              </div>
                            </div>
                        )
                      }
                    })}

                  </div>
              </div>

          )}
        </div>
    );
  }
}